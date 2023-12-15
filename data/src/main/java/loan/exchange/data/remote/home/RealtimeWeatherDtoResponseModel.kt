package loan.exchange.data.remote.home

import kotlinx.serialization.Serializable

@Serializable
data class RealtimeWeatherDtoResponseModel (
    var location: String?,
    var current: String?)