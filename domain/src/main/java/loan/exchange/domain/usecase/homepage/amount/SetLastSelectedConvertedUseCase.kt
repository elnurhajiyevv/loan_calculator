package loan.exchange.domain.usecase.homepage.amount

import loan.exchange.domain.base.BaseSingleUseCase
import loan.exchange.domain.exceptions.ErrorConverter
import loan.exchange.domain.repository.HomeRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SetLastSelectedConvertedUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
    context: CoroutineContext,
    converter: ErrorConverter,
) : BaseSingleUseCase<SetLastSelectedConvertedUseCase.Params, Unit>(context, converter) {

    override fun invoke(params: Params) {
        return homeRepository.setLastSelectedConverter(params.lastSelectedConverted)
    }

    data class Params(val lastSelectedConverted: String)

}