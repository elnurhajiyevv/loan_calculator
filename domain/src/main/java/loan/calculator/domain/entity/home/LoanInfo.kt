package loan.calculator.domain.entity.home

import kotlinx.serialization.Serializable
import loan.calculator.domain.entity.enum.SELECT_TYPE_LOAN

@Serializable
class LoanInfo (
    var name:String,
    var backgroundColor: Int,
    var startDate:String,
    var paidOff:String,
    var loanAmount:Double,
    var interestRate:Double,
    var frequency:String,
    var totalRepayment:String,
    var termInMonth: Int? = 0,
    var type: String = SELECT_TYPE_LOAN.BLOCK.type
): java.io.Serializable