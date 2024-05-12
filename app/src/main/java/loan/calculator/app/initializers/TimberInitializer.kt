package loan.calculator.app.initializers

import android.app.Application
import timber.log.Timber
import javax.inject.Inject

class TimberInitializer @Inject constructor() : AppInitializer {

    override fun init(application: Application) {
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement) =
                "${super.createStackElementTag(element)}:${element.lineNumber}"
        })
    }
}
