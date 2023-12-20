package loan.calculator.domain.repository


interface SettingRepository {

    fun getLightTheme(): Boolean

    fun setLightTheme(isOn: Boolean)

}