package loan.calculator.domain.entity.home

import kotlinx.serialization.Serializable

@Serializable
class CurrencyResponseModel (
    var currencies: Map<String,SavedModel>
)