package loan.calculator

import loan.calculator.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import loan.calculator.data.repository.SettingPreferences
import loan.calculator.domain.usecase.settingpage.GetAppColorUseCase
import loan.calculator.domain.usecase.settingpage.GetLightThemeUseCase
import loan.calculator.setting.effect.SettingPageEffect
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLightThemeUseCase: GetLightThemeUseCase,
    private val settingPreferences: SettingPreferences,
    private val getAppColorUseCase: GetAppColorUseCase,
) : BaseViewModel<Unit, MainEvent>() {

    fun getAppLang() = settingPreferences.getLanguage().code

    fun getLightTheme() = getLightThemeUseCase.invoke(Unit)

    fun getAppColor() {
        getAppColorUseCase.launch(Unit) {
            onSuccess = {
                postEffect(effect = MainEvent.OnAppColor(it))
            }
        }
    }
}
sealed class MainEvent {

    class OnAppColor(val appColor: Int) : MainEvent()
}