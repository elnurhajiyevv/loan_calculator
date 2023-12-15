package loan.exchange.domain.entity.home

import kotlinx.serialization.Serializable

@Serializable
class CurrencyModel (
    var name:String,
    var description: String?,
    var amount: String? = null
)