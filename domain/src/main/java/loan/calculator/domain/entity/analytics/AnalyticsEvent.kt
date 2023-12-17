package loan.calculator.domain.entity.analytics

interface AnalyticsEvent {
    fun getEventName(): String
    fun getEventParams(): Map<String, String>
}

