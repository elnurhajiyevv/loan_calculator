package loan.exchange.domain.usecase.homepage.amount

import loan.exchange.domain.base.BaseSingleUseCase
import loan.exchange.domain.exceptions.ErrorConverter
import loan.exchange.domain.repository.HomeRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SetLastSelectedAmountUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
    context: CoroutineContext,
    converter: ErrorConverter,
) : BaseSingleUseCase<SetLastSelectedAmountUseCase.Params, Unit>(context, converter) {

    override fun invoke(params: Params) {
        return homeRepository.setLastSelectedAmount(params.lastSelectedAmount)
    }

    data class Params(val lastSelectedAmount: String)

}