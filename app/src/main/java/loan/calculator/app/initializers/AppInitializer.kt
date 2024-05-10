package loan.calculator.app.initializers

import android.app.Application

interface AppInitializer {
    fun init(application: Application)
}