package loan.calculator.data.repository

import loan.calculator.domain.entity.home.LanguageModel
import loan.calculator.domain.repository.SettingRepository
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val settingPreferences: SettingPreferences,
) : SettingRepository {
    override fun getLightTheme() = settingPreferences.isLightTheme

    override fun setLightTheme(isOn: Boolean) {
        settingPreferences.isLightTheme = isOn
    }

    override fun getLanguage() = settingPreferences.getLanguage()

    override fun setLanguage(language: LanguageModel) {
        settingPreferences.setLanguage(language)
    }

    override fun getColor(): Int {
        return settingPreferences.colorValue
    }

    override fun setColor(color: Int) {
        settingPreferences.colorValue = color
    }

    override fun getScreenShot(): Boolean {
        return settingPreferences.isScreenShot
    }

    override fun setScreenShot(isOn: Boolean) {
        settingPreferences.isScreenShot = isOn
    }

    override fun getShowCase(): Boolean {
        return settingPreferences.isShowCase
    }

    override fun setShowCase(value: Boolean) {
        settingPreferences.isShowCase = value
    }

    override fun getShowCase2(): Boolean {
        return settingPreferences.isShowCase2
    }

    override fun setShowCase2(value: Boolean) {
        settingPreferences.isShowCase2 = value
    }

}