package loan.calculator.initializers

import android.app.Application
import com.dynatrace.android.agent.Dynatrace
import com.dynatrace.android.agent.conf.DataCollectionLevel
import com.dynatrace.android.agent.conf.UserPrivacyOptions
import loan.calculator.currency.BuildConfig
import javax.inject.Inject

class DynaTraceInitializer @Inject constructor() : AppInitializer {

    override fun init(application: Application) {
        if (BuildConfig.DEBUG) {
            Dynatrace.applyUserPrivacyOptions(
                UserPrivacyOptions.builder()
                .withDataCollectionLevel(DataCollectionLevel.USER_BEHAVIOR)
                .withCrashReportingOptedIn(true)
                .withCrashReplayOptedIn(true)
                .build());
        }
    }
}
