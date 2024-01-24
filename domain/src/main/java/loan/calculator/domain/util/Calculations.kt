package loan.calculator.domain.util

import kotlin.math.pow

fun calculatePayment(
    amount: Double,
    termInMonth: Int,
    interestRate: Double,
    payment: Double,
    type: SELECT_PART,
    frequency: SELECT_FREQUENCY
): Double {
    when(type){
        SELECT_PART.AMOUNT -> return getLoanPayment(
            totalAmount = payment,
            termInMonth = termInMonth,
            interestRate = interestRate,
            frequency = frequency
        )
        SELECT_PART.RATE -> return getRatePayment()
        SELECT_PART.PERIOD -> return getPeriodPayment().toDouble()
        SELECT_PART.PAYMENT -> return getTotalPayment(
            amount = amount,
            termInMonth = termInMonth,
            interestRate = interestRate,
            frequency = frequency
        )
        else -> return 0.0
    }
}

fun getTotalPayment(amount: Double, termInMonth: Int, interestRate: Double, frequency: SELECT_FREQUENCY): Double{
    // Convert tvInterest rate into a decimal. eg. 3.75% ==> 0.0375
    var interestRate = interestRate
    interestRate /= 100.0

    // Monthly Interest Rate is the yearly rate divided by 12 months
    // Weekly Interest Rate is the yearly rate divided by 52.14 weeks
    // Yearly Interest Rate is the yearly rate divided by 1 year
    // Daily Interest Rate is the yearly rate divided by 365 days
    var rate = 0.0
    when(frequency){
        SELECT_FREQUENCY.MONTHLY -> rate = interestRate / 12.0
        SELECT_FREQUENCY.WEEKLY -> rate = interestRate / 52.14
        SELECT_FREQUENCY.YEARLY -> rate = interestRate
        SELECT_FREQUENCY.DAILY -> rate = interestRate / 365
    }

    // Calculate the payment
    return amount * rate / (1 - (1 + rate).pow(-termInMonth.toDouble()))
}

fun getRatePayment(): Double {
    return 0.0
}

fun getPeriodPayment(): Int {
    return 0
}

fun getLoanPayment(totalAmount: Double, termInMonth: Int, interestRate: Double, frequency: SELECT_FREQUENCY): Double {

    var interestRate = interestRate
    interestRate /= 100.0

    var rate = 0.0
    when(frequency){
        SELECT_FREQUENCY.MONTHLY -> rate = interestRate / 12.0
        SELECT_FREQUENCY.WEEKLY -> rate = interestRate / 52.14
        SELECT_FREQUENCY.YEARLY -> rate = interestRate
        SELECT_FREQUENCY.DAILY -> rate = interestRate / 365
    }

    // Calculate the loan amount
    return (1 - (1 + rate).pow(-termInMonth.toDouble())) * totalAmount / rate
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