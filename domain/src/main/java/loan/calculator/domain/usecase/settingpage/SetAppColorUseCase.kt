package loan.calculator.domain.usecase.settingpage

import loan.calculator.domain.base.BaseSingleUseCase
import loan.calculator.domain.exceptions.ErrorConverter
import loan.calculator.domain.repository.SettingRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SetAppColorUseCase @Inject constructor(
    private val repository: SettingRepository,
    context: CoroutineContext,
    converter: ErrorConverter,
) : BaseSingleUseCase<SetAppColorUseCase.Params, Unit>(context, converter) {

    override fun invoke(params: Params) {
        return repository.setColor(params.color)
    }

    data class Params(val color: Int)

}