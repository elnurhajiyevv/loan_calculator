package loan.calculator.domain.entity.home

import java.io.Serializable
import kotlin.math.pow


class Loan(// Supplied Values:
    val loanAmount: Double,
    termInYears: Int,
    annualInterestRate: Double,
    downPayment: Double,
    tradeInValue: Double,
    salesTaxRate: Double,
    fees: Double
) :
    Serializable {
    private val termInYears: Double
    val interestRate: Double

    // Computed Values:
    private val monthlyPayment: Double
    private val totalLoanPayments: Double
    private val totalLoanInterest: Double
    private val totalCost: Double
    private val termInMonths: Double
    var amortizationItems: Array<AmortizationModel?>

    init {
        this.termInYears = termInYears.toDouble()
        interestRate = annualInterestRate
        amortizationItems = arrayOfNulls(termInYears * 12)
        val taxableAmount = loanAmount - tradeInValue
        val salesTax = taxableAmount * salesTaxRate / 100.0
        val financedAmount = taxableAmount + salesTax + fees - downPayment
        monthlyPayment = calculateMonthlyPayment(financedAmount, termInYears, annualInterestRate)
        termInMonths = (termInYears * 12).toDouble()
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
    }

    private fun calculateMonthlyPayment(
        financedAmount: Double,
        termInYears: Int,
        interestRate: Double
    ): Double {
        // Convert tvInterest rate into a decimal. eg. 3.75% ==> 0.0375
        var interestRate = interestRate
        interestRate /= 100.0

        // Monthly Interest Rate is the yearly rate divided by 12 months
        val monthlyRate = interestRate / 12.0

        // The term of the mLoan in months
        val termInMonths = termInYears * 12

        // Calculate the monthly payment
        return financedAmount * monthlyRate / (1 - (1 + monthlyRate).pow(-termInMonths.toDouble()))
    }
}