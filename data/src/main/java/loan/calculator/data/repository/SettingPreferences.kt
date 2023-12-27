package loan.calculator.data.repository

import android.content.Context
import loan.calculator.common.library.prefs.sharedprefs.MainSharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import loan.calculator.common.extensions.isNull
import loan.calculator.domain.entity.home.LanguageModel
import javax.inject.Inject

class SettingPreferences @Inject constructor(@ApplicationContext context: Context) : MainSharedPreferences(context) {
    override val filename = "auth_prefs"

    companion object {
        const val LIGHT_THEME = "LIGHT_THEME"
        const val LANGUAGE = "LANGUAGE"
    }
    var isLightTheme
        get() = get(LIGHT_THEME, true)
        set(value) = set(LIGHT_THEME, value)

    fun getLanguage(): LanguageModel {
        val format = Json { isLenient = true }
        val defaultValue = LanguageModel("en","USA","English")
        return if(get(LANGUAGE,"").isNullOrEmpty())
            defaultValue
        else
            format.decodeFromString(get(LANGUAGE,""))
    }

    fun setLanguage(model: LanguageModel) {
        val format = Json {
            isLenient = true
        }
        set(LANGUAGE, format.encodeToString(model))
    }
}
