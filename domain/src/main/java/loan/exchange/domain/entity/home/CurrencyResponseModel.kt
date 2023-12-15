package loan.exchange.domain.entity.home

import kotlinx.serialization.Serializable

@Serializable
class CurrencyResponseModel (
    var currencies: Map<String,CurrencyModel>
)