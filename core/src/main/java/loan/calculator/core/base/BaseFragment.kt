package loan.calculator.core.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.viewbinding.ViewBinding
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.material.appbar.MaterialToolbar
import loan.calculator.core.R
import loan.calculator.core.dialog.CustomProgressDialog
import loan.calculator.core.extension.deeplinkNavigate
import loan.calculator.core.tools.NavigationCommand
import loan.calculator.uikit.toolbar.LoanToolbar


abstract class BaseFragment<State, Effect, ViewModel : BaseViewModel<State, Effect>, B : ViewBinding> :
    Fragment(), LifecycleOwner {

    protected abstract val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> B
    private val progressDialog: CustomProgressDialog by lazy { CustomProgressDialog(requireContext()) }

    protected lateinit var viewmodel: ViewModel
    protected abstract fun getViewModelClass(): Class<ViewModel>
    protected open fun getViewModelScope(): ViewModelStoreOwner? = null

    //lateinit var mGoogleSignInClient: GoogleSignInClient
    //lateinit var mGoogleSignInOptions: GoogleSignInOptions

    private fun init() {
        viewmodel = ViewModelProvider(getViewModelScope() ?: requireActivity())[getViewModelClass()]
    }

    lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingCallback.invoke(inflater, container, false)
        binding.root.findViewWithTag<LoanToolbar>("toolbar")?.let {
            it.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
        return binding.root
    }


    protected open val bindViews: B.() -> Unit = {}


    protected open val noInternetDialog: DialogFragment? by lazy {
        BaseFragmentBottomSheetDialog.build(
            title = R.string.no_internet_title,
            text = R.string.no_internet_error_text,
            action = { it.dismiss() }
        )
    }

    protected open fun observeState(state: State) {
        // override when observing
    }

    protected open fun observeEffect(effect: Effect) {
        // override when observing
    }

    fun launchOnLifecycleScope(execute: suspend () -> Unit) {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            execute()
        }
    }

    protected open fun showNoInternet() {
        if (noInternetDialog?.isAdded == false)
            noInternetDialog?.show("internet-error-dialog")
    }

    protected open fun showLoading() {
        progressDialog.show()
    }

    protected open fun hideLoading() {
        progressDialog.dismiss()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideKeyboard()
        bindViews(binding)
        handleDialogState()
    }


    @SuppressLint("RestrictedApi")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewmodel.state.observe(viewLifecycleOwner, ::observeState)
        viewmodel.effect.observe(viewLifecycleOwner, ::observeEffect)
        viewmodel.commonEffect.observe(viewLifecycleOwner) {
            when (it) {
                is NoInternet -> showNoInternet()
                else -> error("Unknown BaseEffect: $it")
            }
        }

        viewmodel.navigationCommands.observe(viewLifecycleOwner) { command ->
            when (command) {
                is NavigationCommand.To -> {
                    command.extras?.let { extras ->
                        findNavController().navigate(
                            command.directions,
                            extras
                        )
                    } ?: run {
                        findNavController().navigate(
                            command.directions
                        )
                    }
                }
                is NavigationCommand.BackTo -> findNavController().getBackStackEntry(command.destinationId)
                is NavigationCommand.Back -> findNavController().popBackStack()
                is NavigationCommand.ToRoot -> findNavController().popBackStack(
                    findNavController().backQueue.first().destination.id,
                    false
                )
                is NavigationCommand.Deeplink -> findNavController().deeplinkNavigate(
                    direction = command.deeplink,
                    extras = command.extras,
                    isInclusive = command.isInclusive
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        if (savedInstanceState == null) {
            viewmodel.setArguments(arguments)
            viewmodel.loadArguments()
        }
    }

    protected fun getColor(@ColorRes colorRes: Int): Int {
        return ContextCompat.getColor(requireContext(), colorRes)
    }

    protected fun <T : DialogFragment> T.show(tag: String? = null): T {
        this.show(this@BaseFragment.parentFragmentManager, tag)
        return this
    }

    open fun onNewIntent(intent: Intent?) {}

    protected fun themeColor(@AttrRes id: Int, alternative: Int = Color.TRANSPARENT): Int {
        val typedValue = TypedValue()
        val theme = context?.theme ?: return alternative
        theme.resolveAttribute(id, typedValue, true)
        return typedValue.data
    }

    protected fun <T> LiveData<T>.observe(block: (T) -> Unit) {
        observe(viewLifecycleOwner, block)
    }

    protected fun hideKeyboard() {
        if (!this.isAdded) return

        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val focusedView = requireActivity().currentFocus ?: View(requireActivity())
        imm.hideSoftInputFromWindow(focusedView.windowToken, 0)
    }

    protected fun showKeyboard() {
        if (!this.isAdded) return

        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val focusedView = requireActivity().currentFocus ?: View(requireActivity())
        imm.showSoftInput(focusedView, 0)
    }

    inline fun <R> withBinding(block: B.() -> R): R {
        return binding.block()
    }

    private fun handleDialogState() {
        viewmodel.isLoading.observe(viewLifecycleOwner) { state ->
            if (state) {
                showLoading()
                hideKeyboard()
            } else {
                hideLoading()
            }
        }
    }

    fun setToolbarCustomBackButton(resId: Int) {
        binding.root.findViewWithTag<MaterialToolbar>("toolbar")?.let {
            it.navigationIcon = ContextCompat.getDrawable(requireContext(), resId)
        }
    }

    /*fun startGoogleSign(){
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        mGoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), mGoogleSignInOptions)
        startGoogleSignIn()
    }

    protected fun startGoogleSignIn(){
        var intent = mGoogleSignInClient.signInIntent
        startActivityForResult(intent,1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1000) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(
                ApiException::class.java
            )
            // Signed in successfully, move to main screen add code here
            navigateTo(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("BaseFragment:java", "signInResult:failed code=" + e.statusCode)
        }
    }*/

    protected open fun navigateTo(account:GoogleSignInAccount) {
        // override when observing
    }

}