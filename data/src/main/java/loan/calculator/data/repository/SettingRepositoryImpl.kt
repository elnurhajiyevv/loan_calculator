package loan.calculator.data.repository

import loan.calculator.domain.repository.SettingRepository
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val authPreferences: AuthPreferences,
) : SettingRepository {
    override fun getLightTheme() = authPreferences.isLightTheme

    override fun setLightTheme(isOn: Boolean) {
        authPreferences.isLightTheme = isOn
    }

}