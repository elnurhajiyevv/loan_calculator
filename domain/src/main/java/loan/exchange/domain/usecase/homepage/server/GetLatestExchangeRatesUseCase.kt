package loan.exchange.domain.usecase.homepage.server

import loan.exchange.domain.base.BaseUseCase
import loan.exchange.domain.entity.home.response.LatestRatesResponseModel
import loan.exchange.domain.exceptions.ErrorConverter
import loan.exchange.domain.repository.HomeRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetLatestExchangeRatesUseCase @Inject constructor(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val repository: HomeRepository
) : BaseUseCase<GetLatestExchangeRatesUseCase.Param, LatestRatesResponseModel>(context, converter) {

    class Param(var base: String, var currencies: String? = null)

    override suspend fun executeOnBackground(params: Param) = repository.getLatestExchangeRates(base = params.base, currencies = params.currencies)

}