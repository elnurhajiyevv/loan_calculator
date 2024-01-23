package loan.calculator.domain.entity.home

import kotlinx.serialization.Serializable

@Serializable
class LoanInfo (
    var name:String,
    var backgroundColor: Int,
    var startDate:String,
    var paidOff:String,
    var loanAmount:String,
    var interestRate:String,
    var frequency:String,
    var totalRepayment:String
): java.io.Serializable