package loan.calculator.domain.entity.home.response

import kotlinx.serialization.Serializable

@Serializable
class LatestRatesResponseModel (
    val success: Boolean? = null,
    val timestamp: Long? = null,
    val date: String? = null,
    val base: String? = null,
    val result: Map<String,String>?,
)