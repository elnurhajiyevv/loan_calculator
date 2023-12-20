package  loan.calculator.setting.viewmodel

import loan.calculator.core.base.BaseViewModel
import loan.calculator.setting.effect.SettingPageEffect
import loan.calculator.setting.state.SettingPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import loan.calculator.domain.entity.home.LanguageModel
import loan.calculator.domain.usecase.settingpage.GetAppVersionUseCase
import loan.calculator.domain.usecase.settingpage.GetLightThemeUseCase
import loan.calculator.domain.usecase.settingpage.SetLightThemeUseCase
import javax.inject.Inject

@HiltViewModel
class SettingPageViewModel @Inject constructor(
    private val getAppVersionUseCase: GetAppVersionUseCase,
    private val getLightThemeUseCase: GetLightThemeUseCase,
    private val setLightThemeUseCase: SetLightThemeUseCase
) : BaseViewModel<SettingPageState, SettingPageEffect>() {


    fun getCurrentApplicationVersionName() {
        getAppVersionUseCase.launch(Unit) {
            onSuccess = {
                postEffect(effect = SettingPageEffect.OnAppVersion(it))
            }
        }
    }

    fun getLightTheme() = getLightThemeUseCase.invoke(Unit)

    fun setLightTheme(isOn:Boolean){
        setLightThemeUseCase.invoke(SetLightThemeUseCase.Params(isLightTheme = isOn))
    }

    fun getLanguage(){
        var list = arrayListOf<LanguageModel>()
        launchAll {
            list.add(LanguageModel("USA","English"))
            list.add(LanguageModel("RU","Russian"))
            list.add(LanguageModel("TR","Turkish"))
            list.add(LanguageModel("GE","Georgian"))
            postState(state = SettingPageState.ListOfLanguage(list))
        }
    }
}
