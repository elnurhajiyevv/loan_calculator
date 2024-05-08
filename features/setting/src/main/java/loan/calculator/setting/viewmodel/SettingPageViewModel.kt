package  loan.calculator.setting.viewmodel

import loan.calculator.core.base.BaseViewModel
import loan.calculator.setting.effect.SettingPageEffect
import loan.calculator.setting.state.SettingPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import loan.calculator.common.library.changelang.Locales
import loan.calculator.domain.entity.home.LanguageModel
import loan.calculator.domain.usecase.settingpage.GetAppColorUseCase
import loan.calculator.domain.usecase.settingpage.GetAppVersionUseCase
import loan.calculator.domain.usecase.settingpage.GetLanguageUseCase
import loan.calculator.domain.usecase.settingpage.GetLightThemeUseCase
import loan.calculator.domain.usecase.settingpage.GetPackageNameUseCase
import loan.calculator.domain.usecase.settingpage.GetScreenShotUseCase
import loan.calculator.domain.usecase.settingpage.SetAppColorUseCase
import loan.calculator.domain.usecase.settingpage.SetLanguageUseCase
import loan.calculator.domain.usecase.settingpage.SetLightThemeUseCase
import loan.calculator.domain.usecase.settingpage.SetScreenShotUseCase
import javax.inject.Inject

@HiltViewModel
class SettingPageViewModel @Inject constructor(
    private val getAppVersionUseCase: GetAppVersionUseCase,
    private val getLightThemeUseCase: GetLightThemeUseCase,
    private val setLightThemeUseCase: SetLightThemeUseCase,
    private val getPackageNameUseCase: GetPackageNameUseCase,
    private val getLanguageUseCase: GetLanguageUseCase,
    private val setLanguageUseCase: SetLanguageUseCase,
    private val setAppColorUseCase: SetAppColorUseCase,
    private val getScreenShotUseCase: GetScreenShotUseCase,
    private val setScreenShotUseCase: SetScreenShotUseCase,
) : BaseViewModel<SettingPageState, SettingPageEffect>() {


    fun getCurrentApplicationVersionName() {
        getAppVersionUseCase.launch(Unit) {
            onSuccess = {
                postEffect(effect = SettingPageEffect.OnAppVersion(it))
            }
        }
    }

    fun setAppColor(color: Int){
        setAppColorUseCase.invoke(SetAppColorUseCase.Params(color = color))
    }

    fun getPackageName() {
        getPackageNameUseCase.launch(Unit) {
            onSuccess = {
                postEffect(effect = SettingPageEffect.OnPackageName(it))
            }
        }
    }

    fun getLightTheme() = getLightThemeUseCase.invoke(Unit)

    fun setLightTheme(isOn:Boolean){
        setLightThemeUseCase.invoke(SetLightThemeUseCase.Params(isLightTheme = isOn))
    }

    fun getLanguage() = getLanguageUseCase.invoke(Unit)

    fun setLanguage(language:LanguageModel){
        setLanguageUseCase.invoke(SetLanguageUseCase.Params(language = language))
    }

    fun getScreenShot() = getScreenShotUseCase.invoke(Unit)

    fun setScreenShot(isOn:Boolean){
        setScreenShotUseCase.invoke(SetScreenShotUseCase.Params(isOn = isOn))
    }

    fun getLanguageList(){
        var list = arrayListOf<LanguageModel>()
        list.clear()
        launchAll {
            list.add(LanguageModel("en","USA",LANGUAGENAME.ENGLISH.type))
            list.add(LanguageModel("ru","RU",LANGUAGENAME.RUSSIAN.type))
            list.add(LanguageModel("tr","TR",LANGUAGENAME.TURKISH.type))
            list.add(LanguageModel("ge","GE",LANGUAGENAME.GEORGIAN.type))
            list.forEach {
                if(it.code == getLanguage().code)
                    it.isSelected = true
            }
            postEffect(effect = SettingPageEffect.ListOfLanguage(list))
        }
    }

    enum class LANGUAGENAME(var type: String) {
        ENGLISH("English"), RUSSIAN("Russian"), TURKISH("Turkish"), GEORGIAN("Georgian")
    }
}
