package loan.calculator.domain.entity.home.response

import kotlinx.serialization.Serializable

@Serializable
class GetSavedLoanResponseModel(
    val name: String?,
    val description: String?
)