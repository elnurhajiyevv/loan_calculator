package loan.calculator.domain.entity.saved

import kotlinx.serialization.Serializable
import loan.calculator.domain.entity.enum.SELECT_TYPE_LOAN
import loan.calculator.domain.entity.home.AmortizationModel

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
    val totalPayment: String?,
    val period: String? = null,
    var selected: Boolean = false,
    var termInMonth: Int?
): java.io.Serializable