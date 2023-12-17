package loan.calculator.core.base

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import androidx.navigation.Navigator
import loan.calculator.core.tools.NavigationCommand
import loan.calculator.core.tools.SingleLiveEvent
import loan.calculator.domain.exceptions.NetworkError
import loan.calculator.domain.exceptions.ServerError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

open class BaseViewModel<State, Effect> : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _state = MutableLiveData<State>()

    private val _effect = SingleLiveEvent<Effect>()

    private val _commonEffect = SingleLiveEvent<BaseEffect>()

    val commonEffect: LiveData<BaseEffect>
        get() = _commonEffect

    val navigationCommands = SingleLiveEvent<NavigationCommand>()

    val state: LiveData<State>
        get() = _state

    val effect: SingleLiveEvent<Effect>
        get() = _effect

    @SuppressLint("NullSafeMutableLiveData")
    protected fun postState(state: State) {
        _state.value = state
    }

    @SuppressLint("NullSafeMutableLiveData")
    protected fun postEffect(effect: Effect) {
        _effect.postValue(effect)
    }

    fun navigate(directions: NavDirections, extras: Navigator.Extras? = null) {
        navigationCommands.postValue(NavigationCommand.To(directions, extras))
    }

    fun navigate(command: NavigationCommand) {
        navigationCommands.postValue(command)
    }

    private var arguments: Bundle? = null

    fun setArguments(data: Bundle?) {
        arguments = data
    }

    fun getArguments(): Bundle? {
        return arguments
    }

    /**
     * Override if arguments will be used
     */
    open fun loadArguments() {}

    fun launchAll(
        context: CoroutineContext = EmptyCoroutineContext,
        loadingHandle: (Boolean) -> Unit = ::handleLoading,
        errorHandle: (Throwable) -> Unit = ::handleError,
        block: suspend () -> Unit
    ) {
        viewModelScope.launch(context) {
            try {
                loadingHandle(true)
                block()
            } catch (t: Throwable) {
                errorHandle(t)
            } finally {
                loadingHandle(false)
            }
        }
    }

    protected fun handleError(t: Throwable) {
        Timber.e(t)
        when (t) {
            is ServerError.ServerIsDown -> _commonEffect.postValue(BackEndError())
            is ServerError.ClientError -> _commonEffect.postValue(BackEndError(MessageError(t.code,t.message)))
            is NetworkError -> _commonEffect.postValue(NoInternet)
            is ServerError.NotAuthorized -> _commonEffect.postValue(NotAuthorized)
            else -> _commonEffect.postValue(UnknownError(cause = t))
        }
    }

    protected fun <T> Flow<T>.handleLoading(loadingHandle: (Boolean) -> Unit = ::handleLoading): Flow<T> =
        flow {
            this@handleLoading
                .onStart { loadingHandle(true) }
                .onCompletion { loadingHandle(false) }
                .collect { value ->
                    loadingHandle(false)
                    emit(value)
                }
        }

    protected fun <T> Flow<T>.handleError(): Flow<T> = catch { handleError(it) }

    protected fun <T> Flow<T>.launch(
        scope: CoroutineScope = viewModelScope,
        loadingHandle: (Boolean) -> Unit = ::handleLoading
    ): Job =
        this.handleError()
            .handleLoading(loadingHandle)
            .launchIn(scope)

    protected fun <T> Flow<T>.launchNoLoading(scope: CoroutineScope = viewModelScope): Job =
        this.handleError()
            .launchIn(scope)

    protected fun <P, R, U : loan.calculator.domain.base.BaseUseCase<P, R>> U.launch(
        param: P,
        loadingHandle: (Boolean) -> Unit = ::handleLoading,
        block: loan.calculator.domain.base.CompletionBlock<R> = {}
    ) {
        viewModelScope.launch {
            val actualRequest = loan.calculator.domain.base.BaseUseCase.Request<R>().apply(block)

            val proxy: loan.calculator.domain.base.CompletionBlock<R> = {
                onStart = {
                    loadingHandle(true)
                    actualRequest.onStart?.invoke()
                }
                onSuccess = {
                    actualRequest.onSuccess(it)
                }
                onCancel = {
                    actualRequest.onCancel?.invoke(it)
                }
                onTerminate = {
                    loadingHandle(false)
                    actualRequest.onTerminate
                }
                onError = {
                    Timber.e(it)
                    if (it is ServerError.NotAuthorized)
                        handleError(it)
                    else
                        actualRequest.onError?.invoke(it) ?: handleError(it)
                }
            }
            execute(param, proxy)
        }
    }

    protected fun <P, R, U : loan.calculator.domain.base.BaseUseCase<P, R>> U.launchNoLoading(
        param: P,
        block: loan.calculator.domain.base.CompletionBlock<R> = {}
    ) {
        viewModelScope.launch {
            val actualRequest = loan.calculator.domain.base.BaseUseCase.Request<R>().apply(block)


            val proxy: loan.calculator.domain.base.CompletionBlock<R> = {
                onStart = actualRequest.onStart
                onSuccess = actualRequest.onSuccess
                onCancel = actualRequest.onCancel
                onTerminate = actualRequest.onTerminate
                onError = {
                    Timber.e(it)
                    actualRequest.onError?.invoke(it) ?: handleError(it)
                }
            }
            execute(param, proxy)
        }
    }

    protected fun <P, R, U : loan.calculator.domain.base.BaseUseCase<P, R>> U.launchUnsafe(
        param: P,
        block: loan.calculator.domain.base.CompletionBlock<R> = {}
    ) {
        viewModelScope.launch { execute(param, block) }
    }

    protected fun handleLoading(state: Boolean) {
        _isLoading.postValue(state)
    }

    suspend fun <T> launchWithAsync(
        loadingHandle: (Boolean) -> Unit = ::handleLoading,
        errorHandle: (exception: Throwable) -> Unit = ::handleError,
        block: () -> List<Deferred<T>>
    ) {
        try {
            loadingHandle(true)
            block().map { it.await() }
        } catch (t: Throwable) {
            errorHandle(t)
        } finally {
            loadingHandle(false)
        }
    }
}

suspend fun <P, R> loan.calculator.domain.base.BaseUseCase<P, R>.getWith(param: P): R? {
    var result: R? = null
    val block: loan.calculator.domain.base.CompletionBlock<R> = {
        onSuccess = { result = it }
        onError = { throw it }
    }
    execute(param, block)
    return result
}