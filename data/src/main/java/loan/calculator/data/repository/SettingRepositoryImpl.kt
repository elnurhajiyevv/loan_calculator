package loan.calculator.data.repository

import android.content.Context
import loan.calculator.domain.repository.SettingRepository
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val context: Context,
    private val authPreferences: AuthPreferences,
) : SettingRepository {

}