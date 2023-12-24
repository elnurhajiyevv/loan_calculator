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

}