package loan.calculator.data.remote.home

import androidx.annotation.Keep

@Keep
data class SearchModelDto(
    val id: Int,
    val name: String,
    val region: String?,
    val country: String?,
    val lat: String?,
    val lon: String?,
    val url: String?
)

enum class WeatherSearchOptionDto(val rawValue: String?) {
    WEATHER_COUNTRIES("WEATHER_COUNTRIES"),
    ALL(null),
}