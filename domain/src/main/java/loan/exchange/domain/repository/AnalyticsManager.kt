package loan.exchange.domain.repository

import loan.exchange.domain.entity.analytics.AnalyticsEvent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalyticsManager @Inject constructor(
    private val trackers: AnalyticsRepositories,
) {

    fun setUserId(userId: String?) {
        trackers.forEach { tracker ->
            tracker.setUserId(userId)
        }
    }

    fun trackEvent(event: AnalyticsEvent) {
        trackers.forEach { tracker ->
            tracker.trackEvent(event)
        }
    }
}

interface AnalyticsRepository {
    fun trackEvent(event: AnalyticsEvent)
    fun setUserId(userId: String?)
    fun setUserProperty(name: String, value: String?)
    fun setDefaultEventParameters(params: Map<String, String>?)
}

typealias AnalyticsRepositories = Set<@JvmSuppressWildcards AnalyticsRepository>

