package loan.calculator.domain.usecase.savepage

import loan.calculator.domain.base.BaseSingleUseCase
import loan.calculator.domain.entity.unit.IconModel
import loan.calculator.domain.exceptions.ErrorConverter
import loan.calculator.domain.repository.SaveRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SetIconModelUseCase @Inject constructor(
    private val repository: SaveRepository,
    context: CoroutineContext,
    converter: ErrorConverter,
) : BaseSingleUseCase<SetIconModelUseCase.Params, Unit>(context, converter) {

    override fun invoke(params: Params) {
        return repository.setIconModel(params.model)
    }

    data class Params(val model: IconModel)

}