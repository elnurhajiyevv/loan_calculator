package loan.exchange.domain.entity.home

data class SearchCityModel(
    val id: Int,
    val name: String,
    val region: String?,
    val country: String?,
    val lat: String?,
    val lon: String?,
    val url: String?
)
