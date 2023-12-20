package loan.calculator

import loan.calculator.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import loan.calculator.domain.usecase.settingpage.GetLightThemeUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLightThemeUseCase: GetLightThemeUseCase,
) : BaseViewModel<Unit, MainEvent>() {

    fun getLightTheme() = getLightThemeUseCase.invoke(Unit)
}
sealed class MainEvent {
}