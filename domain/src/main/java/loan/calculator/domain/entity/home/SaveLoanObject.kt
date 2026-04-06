package loan.calculator.domain.entity.home

data class SaveLoanObject(
    var amount: String,
    var period: String,
    var rate: String,
    var paymentAmount: String,
    var frequencyRate: String,
    var termInMonth: String,
    var totalInterest: String,
    var totalPayment: String
)