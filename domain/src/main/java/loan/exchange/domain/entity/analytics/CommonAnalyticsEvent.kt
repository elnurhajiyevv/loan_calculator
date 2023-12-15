package loan.exchange.domain.entity.analytics

sealed interface CommonAnalyticsEvent : AnalyticsEvent {

    data class ScreenView(
        val screenName: String?,
        val screenClass: String,
    ) : CommonAnalyticsEvent {
        override fun getEventName() = "screen_view"
        override fun getEventParams() = buildMap {
            if (!screenName.isNullOrBlank()) {
                put("screen_name", screenName)
            }
            put("screen_class", screenClass)
        }
    }
}