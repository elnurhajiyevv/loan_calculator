package loan.exchange.domain.entity.home

import kotlinx.serialization.Serializable
@Serializable
class HistoricalResponseModel (
    var success: Boolean?,
    var terms: String?,
    var privacy: String?,
    var timestamp: String?,
    var date: String?,
    var base: String?,
    var rates: Map<String,String>?,
)