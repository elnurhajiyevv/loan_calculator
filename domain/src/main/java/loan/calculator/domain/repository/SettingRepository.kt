package loan.calculator.domain.repository

import loan.calculator.domain.entity.home.LanguageModel


interface SettingRepository {

    fun getLightTheme(): Boolean

    fun setLightTheme(isOn: Boolean)

    fun getLanguage(): LanguageModel

    fun setLanguage(language: LanguageModel)

}