package loan.exchange.domain.usecase.homepage.server

import loan.exchange.domain.base.BaseUseCase
import loan.exchange.domain.entity.home.response.ConvertCurrencyResponseModel
import loan.exchange.domain.exceptions.ErrorConverter
import loan.exchange.domain.repository.HomeRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetConvertedCurrencyUseCase @Inject constructor(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val repository: HomeRepository
) : BaseUseCase<GetConvertedCurrencyUseCase.Param, ConvertCurrencyResponseModel>(context, converter) {

    class Param(val from: String, var to: String, val amount: String)

    override suspend fun executeOnBackground(params: Param) = repository.getConvertedCurrency(from = params.from, to = params.to, amount = params.amount)

}