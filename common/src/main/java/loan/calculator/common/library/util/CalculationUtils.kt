package loan.calculator.common.library.util

import loan.calculator.domain.entity.home.AmortizationModel
import kotlin.math.pow

/*
 * Created by Elnur on on 28.04.24, 15.
 * Copyright (c) 2024 . All rights reserved to Elnur.
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */
fun calculateAmortization(loanAmount: Double,termInMonths: Int, annualInterestRate: Double): Array<AmortizationModel?>{

    var tradeInValue = 0.0
    var salesTaxRate = 0.0
    var fees = 0.0
    var downPayment = 0.0

    val monthlyPayment: Double
    val totalLoanPayments: Double
    val totalLoanInterest: Double
    val totalCost: Double

    var amortizationItems: Array<AmortizationModel?> = arrayOfNulls(termInMonths)
    val taxableAmount = loanAmount - tradeInValue
    val salesTax = taxableAmount * salesTaxRate / 100.0
    val financedAmount = taxableAmount + salesTax + fees - downPayment
    monthlyPayment = calculateMonthlyPayment(financedAmount, termInMonths, annualInterestRate)
    totalLoanPayments = monthlyPayment * termInMonths
    totalLoanInterest = totalLoanPayments - financedAmount
    totalCost = loanAmount + totalLoanInterest + salesTax + fees
    var principal = financedAmount
    val monthlyInterestRate = annualInterestRate / 12.0
    var month = 1
    while (month <= termInMonths) {

        // Compute amount paid and new balance for each payment period
        val interestPaid = principal * (monthlyInterestRate / 100)
        val principalPaid = monthlyPayment - interestPaid
        val newBalance = principal - principalPaid

        // Output the data item
        val item = AmortizationModel(month, principal, interestPaid, principalPaid, newBalance)
        amortizationItems[month - 1] = item

        // Update the balance
        principal = newBalance
        month++
    }
    return amortizationItems
}

fun calculateMonthlyPayment(
    financedAmount: Double,
    termInMonths: Int,
    interestRate: Double
): Double {
    // Convert tvInterest rate into a decimal. eg. 3.75% ==> 0.0375
    var interestRate = interestRate
    interestRate /= 100.0

    // Monthly Interest Rate is the yearly rate divided by 12 months
    val monthlyRate = interestRate / 12.0

    // Calculate the monthly payment
    return financedAmount * monthlyRate / (1 - (1 + monthlyRate).pow(-termInMonths.toDouble()))
}