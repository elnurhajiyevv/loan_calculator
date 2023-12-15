package loan.exchange.domain.entity.home

data class SearchModel(
    val id: Int,
    val name: String,
    val region: String?,
    val country: String?,
    val lat: String?,
    val lon: String?,
    val url: String?
)

sealed interface SearchScope {
    object WeatherCountries : SearchOption
    object All : SearchOption

    sealed interface SearchOption : SearchScope
}