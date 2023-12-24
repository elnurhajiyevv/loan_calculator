package loan.calculator.domain.entity.saved

import kotlinx.serialization.Serializable

@Serializable
class GetSavedLoanModel(
    val name: String?,
    val code: String?,
    val description: String?
)