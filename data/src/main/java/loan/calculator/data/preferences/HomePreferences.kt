package loan.calculator.data.preferences

import android.content.Context
import loan.calculator.common.library.prefs.sharedprefs.EncryptedSharedPreference
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomePreferences @Inject constructor(@ApplicationContext context: Context) :
    EncryptedSharedPreference(context) {

    override val filename = "home_prefs"
    private val _latestExchangeState = MutableStateFlow(getLatestExchange())

    private fun getLatestExchange(): String {
        val format = Json {
            isLenient = true
        }
        return prefs.getString(LATEST_EXCHANGE, null)?.let {
            try {
                format.decodeFromString<String>(it)
            } catch (ex: Exception) {
                ""
            }
        } ?: ""
    }
    suspend fun cacheLatestExchange(latestExchangeCurrency: String) {
        val format = Json {
            isLenient = true
        }
        val encoded = format.encodeToString(latestExchangeCurrency)
        prefs.edit().putString(LATEST_EXCHANGE, encoded).apply()
        _latestExchangeState.emit(latestExchangeCurrency)
    }
    fun observeLatestExchange(): Flow<String> {
        return _latestExchangeState
    }

    companion object {
        const val LATEST_EXCHANGE = "latest_exchange"

    }
    fun clearAccountCache() {
        clear()
    }
}
