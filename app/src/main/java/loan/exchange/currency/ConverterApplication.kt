/*
 * Created by Elnur Hajiyev on 04/02/22, 6:31 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.exchange.currency

import android.app.Application
import loan.exchange.currency.initializers.AppInitializers
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ConverterApplication : Application() {

    @Inject lateinit var initializers: AppInitializers
    private val ONESIGNAL_APP_ID = ""

    override fun onCreate() {
        super.onCreate()
        initializers.init(this)
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
    }


}