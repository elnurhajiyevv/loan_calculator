package loan.calculator.domain.entity.saved

import kotlinx.serialization.Serializable

@Serializable
class GetSavedLoanModel(
    val name: String?,
    val code: String?,
    val description: String?,
    val type: String?,
    val background: String?,
    val src: String? = "other",
    val startDate: String?,
    val paidOff: String?,
    val loanAmount: String?,
    val interestRate: String?,
    val compoundingFrequency: String?,
    val totalPayment: String?
)