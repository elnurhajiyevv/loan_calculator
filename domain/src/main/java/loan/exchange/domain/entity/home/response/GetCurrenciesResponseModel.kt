package loan.exchange.domain.entity.home.response

import kotlinx.serialization.Serializable

@Serializable
class GetCurrenciesResponseModel(
    val success: Boolean?,
    val result: Map<String, String>?
)