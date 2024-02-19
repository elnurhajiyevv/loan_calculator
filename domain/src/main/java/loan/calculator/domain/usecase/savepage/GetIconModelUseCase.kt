package loan.calculator.domain.usecase.savepage

import loan.calculator.domain.base.BaseSingleUseCase
import loan.calculator.domain.entity.home.LanguageModel
import loan.calculator.domain.entity.unit.IconModel
import loan.calculator.domain.exceptions.ErrorConverter
import loan.calculator.domain.repository.SaveRepository
import loan.calculator.domain.repository.SettingRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetIconModelUseCase @Inject constructor(
    private val repository: SaveRepository,
    context: CoroutineContext,
    converter: ErrorConverter,
) : BaseSingleUseCase<Unit, IconModel>(context, converter) {

    override fun invoke(params: Unit): IconModel {
        return repository.getIconModel()
    }
}