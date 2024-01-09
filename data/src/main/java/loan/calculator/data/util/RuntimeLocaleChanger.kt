package loan.calculator.data.util

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import loan.calculator.data.repository.SettingPreferences
import loan.calculator.domain.entity.home.LanguageModel
import java.util.Locale

object RuntimeLocaleChanger {

    fun wrapContext(context: Context): Context {

        val savedLocale = Locale(SettingPreferences(context).getLanguage().code) // load the user language from SharedPreferences

        // as part of creating a new context that contains the new locale we also need to override the default locale.
        Locale.setDefault(savedLocale)

        // create new configuration with the saved locale
        val newConfig = Configuration()
        newConfig.setLocale(savedLocale)

        return context.createConfigurationContext(newConfig)
    }

    fun overrideLocale(context: Context) {

        val savedLocale =
            Locale(SettingPreferences(context).getLanguage().code) // nothing to do in this case

        // as part of creating a new context that contains the new locale we also need to override the default locale.
        Locale.setDefault(savedLocale)

        // create new configuration with the saved locale
        val newConfig = Configuration()
        newConfig.setLocale(savedLocale)

        // override the locale on the given context (Activity, Fragment, etc...)
        context.resources.updateConfiguration(newConfig, context.resources.displayMetrics)

        // override the locale on the application context
        if (context != context.applicationContext) {
            context.applicationContext.resources.run { updateConfiguration(newConfig, displayMetrics) }
        }
    }
}