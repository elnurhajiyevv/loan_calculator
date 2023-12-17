package loan.calculator.domain.entity.home.response

import kotlinx.serialization.Serializable

@Serializable
class ConvertCurrencyResponseModel (
    var success: Boolean?,
    val request: RequestResponseModel?,
    val meta: MetaResponseModel?,
    val result: Long?
)

@Serializable
class RequestResponseModel (
    val from: String?,
    val to: String?,
    val amount: String?,
)

@Serializable
class MetaResponseModel (
    val timestamp: Long?,
    val rates: RatesResponseModel?,
    val formated: FormattedResponseModel?,
)

@Serializable
class FormattedResponseModel (
    val from: String?,
    val to: String?,
)

@Serializable
class RatesResponseModel (
    val from: Long?,
    val to: Long?,
)
