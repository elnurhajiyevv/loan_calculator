package loan.exchange.domain.usecase.homepage.amount

import loan.exchange.domain.base.BaseSingleUseCase
import loan.exchange.domain.exceptions.ErrorConverter
import loan.exchange.domain.repository.HomeRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SetLastSelectedRateUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
    context: CoroutineContext,
    converter: ErrorConverter,
) : BaseSingleUseCase<SetLastSelectedRateUseCase.Params, Unit>(context, converter) {

    override fun invoke(params: Params) {
        return homeRepository.setLastRate(params.lastSelectedRate)
    }
    data class Params(val lastSelectedRate: Float)
}