package loan.calculator.domain.repository

import loan.calculator.domain.entity.home.LanguageModel


interface SettingRepository {

    fun getLightTheme(): Boolean

    fun setLightTheme(isOn: Boolean)

    fun getLanguage(): LanguageModel

    fun setLanguage(language: LanguageModel)

    fun getColor(): Int

    fun setColor(color: Int)

    fun getScreenShot(): Boolean

    fun setScreenShot(isOn: Boolean)

    fun getShowCase(): Boolean

    fun setShowCase(value: Boolean)

    fun getShowCase2(): Boolean

    fun setShowCase2(value: Boolean)

}