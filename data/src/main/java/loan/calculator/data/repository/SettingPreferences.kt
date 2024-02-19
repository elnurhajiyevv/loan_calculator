package loan.calculator.data.repository

import android.content.Context
import loan.calculator.common.library.prefs.sharedprefs.MainSharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import loan.calculator.common.extensions.isNull
import loan.calculator.domain.entity.enum.SELECT_TYPE_LOAN
import loan.calculator.domain.entity.home.LanguageModel
import loan.calculator.domain.entity.unit.IconModel
import javax.inject.Inject

class SettingPreferences @Inject constructor(@ApplicationContext context: Context) : MainSharedPreferences(context) {
    override val filename = "auth_prefs"

    companion object {
        const val LIGHT_THEME = "LIGHT_THEME"
        const val LANGUAGE = "LANGUAGE"
        const val ICON_MODEL = "ICON_MODEL"
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


    fun getIconModel(): IconModel {
        val format = Json { isLenient = true }
        val defaultValue =
            IconModel(
                iconResource = SELECT_TYPE_LOAN.HOME,
                backgroundColor = 0x33000000,
                isSelected = false)
        return if(get(ICON_MODEL,"").isNullOrEmpty())
            defaultValue
        else
            format.decodeFromString(get(ICON_MODEL,""))
    }

    fun setIconModel(model: IconModel) {
        val format = Json {
            isLenient = true
        }
        set(ICON_MODEL, format.encodeToString(model))
    }
}
