/*
 * Created by Elnur Hajiyev on 04/02/22, 6:31 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.calculator

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import loan.calculator.initializers.AppInitializers
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp
import loan.calculator.data.repository.SettingPreferences
import java.util.Locale
import javax.inject.Inject

@HiltAndroidApp
class CalculatorApplication : Application() {

    @Inject lateinit var initializers: AppInitializers
    private val ONESIGNAL_APP_ID = ""

    override fun onCreate() {
        super.onCreate()
        initializers.init(this)
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
    }

   /* *//*override fun attachBaseContext(base: Context) {
        super.attachBaseContext(wrapContext(base))
    }*//*

    fun wrapContext(context: Context): Context {

        val savedLocale = createLocaleFromSavedLanguage()
        Locale.setDefault(savedLocale)

        // create new configuration with the saved locale
        val newConfig = Configuration()
        newConfig.setLocale(savedLocale)

        return context.createConfigurationContext(newConfig)
    }

    private fun createLocaleFromSavedLanguage(): Locale {
        var lang = settingPreferences.getLanguage().code
        return Locale("","")
    }*/
}