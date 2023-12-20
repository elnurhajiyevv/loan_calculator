package loan.calculator.domain.usecase.settingpage

import loan.calculator.domain.base.BaseSingleUseCase
import loan.calculator.domain.exceptions.ErrorConverter
import loan.calculator.domain.repository.SettingRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SetLightThemeUseCase @Inject constructor(
    private val repository: SettingRepository,
    context: CoroutineContext,
    converter: ErrorConverter,
) : BaseSingleUseCase<SetLightThemeUseCase.Params, Unit>(context, converter) {

    override fun invoke(params: Params) {
        return repository.setLightTheme(params.isLightTheme)
    }

    data class Params(val isLightTheme: Boolean)

}