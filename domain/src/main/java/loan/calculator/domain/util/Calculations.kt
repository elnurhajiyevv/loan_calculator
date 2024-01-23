package loan.calculator.domain.util

import kotlin.math.pow

fun calculatePayment(
    amount: Double,
    termInMonth: Int,
    interestRate: Double,
    payment: Double,
    type: SELECT_PART
): Double {
    when(type){
        SELECT_PART.AMOUNT -> return getLoanPayment()
        SELECT_PART.RATE -> return getRatePayment()
        SELECT_PART.PERIOD -> return getPeriodPayment().toDouble()
        SELECT_PART.PAYMENT -> return getMonthlyPayment(
            amount = amount,
            termInMonth = termInMonth,
            interestRate = interestRate
        )
        else -> return 0.0
    }
}

fun getMonthlyPayment(amount: Double, termInMonth: Int, interestRate: Double): Double{
    // Convert tvInterest rate into a decimal. eg. 3.75% ==> 0.0375
    var interestRate = interestRate
    interestRate /= 100.0

    // Monthly Interest Rate is the yearly rate divided by 12 months
    val monthlyRate = interestRate / 12.0

    // Calculate the monthly payment
    return amount * monthlyRate / (1 - (1 + monthlyRate).pow(-termInMonth.toDouble()))
}

fun getRatePayment(): Double {
    return 0.0
}

fun getPeriodPayment(): Int {
    return 0
}

fun getLoanPayment(): Double {
    return 0.0
}

enum class SELECT_PART(type: String){
    AMOUNT("amount"),
    PERIOD("period"),
    RATE("rate"),
    PAYMENT("payment")
}

enum class SELECT_FREQUENCY(type: String){
    MONTHLY("Monthly"),
    WEEKLY("Weekly"),
    DAILY("Daily"),
    YEARLY("Yearly")
}