package loan.calculator.app

import androidx.core.os.bundleOf
import loan.calculator.domain.entity.analytics.AnalyticsEvent
import loan.calculator.domain.entity.analytics.CommonAnalyticsEvent
import loan.calculator.domain.repository.AnalyticsRepository
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAnalyticsRepository @Inject constructor() : AnalyticsRepository {

    private val firebaseAnalytics = Firebase.analytics

    override fun trackEvent(event: AnalyticsEvent) {
        when (event) {
            is CommonAnalyticsEvent.ScreenView -> {
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
                    val screenName = event.screenName
                    if (!screenName.isNullOrBlank()) {
                        param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
                    }
                    param(FirebaseAnalytics.Param.SCREEN_CLASS, event.screenClass)
                }
            }
            else -> {
                firebaseAnalytics.logEvent(event.getEventName()) {
                    event.getEventParams().forEach { (key, value) ->
                        param(key, value)
                    }
                }
            }
        }
    }

    override fun setUserId(userId: String?) {
        firebaseAnalytics.setUserId(userId)
    }

    override fun setUserProperty(name: String, value: String?) {
        firebaseAnalytics.setUserProperty(name, value)
    }

    override fun setDefaultEventParameters(params: Map<String, String>?) {
        firebaseAnalytics.setDefaultEventParameters(
            if (params == null) {
                null
            } else {
                bundleOf(
                    *params.map { (name, value) -> name to value }.toTypedArray()
                )
            }
        )
    }
}