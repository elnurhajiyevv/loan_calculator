package loan.exchange.domain.usecase.homepage.amount

import loan.exchange.domain.base.BaseSingleUseCase
import loan.exchange.domain.exceptions.ErrorConverter
import loan.exchange.domain.repository.HomeRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetSelectedAmountUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
    context: CoroutineContext,
    converter: ErrorConverter,
) : BaseSingleUseCase<Unit, String>(context, converter) {

    override fun invoke(params: Unit): String {
        return homeRepository.getLastSelectedAmount()
    }
}