package loan.calculator.domain.usecase.settingpage

import loan.calculator.domain.base.BaseSingleUseCase
import loan.calculator.domain.exceptions.ErrorConverter
import loan.calculator.domain.repository.SettingRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SetScreenShotUseCase @Inject constructor(
    private val repository: SettingRepository,
    context: CoroutineContext,
    converter: ErrorConverter,
) : BaseSingleUseCase<SetScreenShotUseCase.Params, Unit>(context, converter) {

    override fun invoke(params: Params) {
        return repository.setScreenShot(params.isOn)
    }

    data class Params(val isOn: Boolean)

}