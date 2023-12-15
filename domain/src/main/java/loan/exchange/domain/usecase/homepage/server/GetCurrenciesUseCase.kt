package loan.exchange.domain.usecase.homepage.server

import kotlinx.coroutines.flow.Flow
import loan.exchange.domain.base.BaseFlowUseCase
import loan.exchange.domain.base.BaseUseCase
import loan.exchange.domain.entity.home.response.GetCurrenciesResponseModel
import loan.exchange.domain.exceptions.ErrorConverter
import loan.exchange.domain.repository.HomeRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
class RefreshCurrenciesUseCase @Inject constructor(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val repository: HomeRepository
) : BaseUseCase<RefreshCurrenciesUseCase.Param, Unit>(context, converter) {

    class Param()
    override suspend fun executeOnBackground(params: Param) {
        repository.refreshCurrencies()
    }
}

class ObserveCurrenciesUseCase @Inject constructor(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val repository: HomeRepository,
) : BaseFlowUseCase<ObserveCurrenciesUseCase.Param, GetCurrenciesResponseModel>(context, converter) {

    class Param
    override fun createFlow(params: Param): Flow<GetCurrenciesResponseModel> {
        return repository.observeCurrencies()
    }
}