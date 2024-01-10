package  loan.calculator.loan.viewmodel

import loan.calculator.core.base.BaseViewModel
import loan.calculator.loan.effect.LoanPageEffect
import loan.calculator.loan.state.LoanPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import loan.calculator.loan.view.LoanPageFragment
import javax.inject.Inject
import kotlin.math.pow

@HiltViewModel
class LoanPageViewModel @Inject constructor(
) : BaseViewModel<LoanPageState, LoanPageEffect>() {

    var setSelection = LoanPageFragment.SELECT_PART.PAYMENT

    var selectedLoanAmount = 100000.0F
    var selectedLoanPeriodYear = 1
    var selectedLoanPeriodMonth = 0
    var selectedLoanRate = 12
    var selectedLoanPayment = 8884.88

    /*fun getCurrency(){
        observeCurrencyUseCase.execute(ObserveSavedLoanUseCase.Param())
            .filterNotNull()
            .onEach {
                availableCurrency.clear()
               *//* it.result?.forEach {
                    availableCurrency.add(SavedModel(name = it.key, description = it.value))
                }*//*
                postState(LoanPageState.GetCurrency((availableCurrency)))
            }
            .launchNoLoading()
        refreshCurrencyUseCase.launch(RefreshSavedLoanUseCase.Param())
    }*/

    fun calculateMonthly(total: Double, period: Double): Double {
        return total/period
    }

    fun calculateMonthlyPayment(
        amount: Double,
        termInMonth: Int,
        termInYear: Int,
        interestRate: Double
    ): Double {
        // Convert tvInterest rate into a decimal. eg. 3.75% ==> 0.0375
        var interestRate = interestRate
        interestRate /= 100.0

        // Monthly Interest Rate is the yearly rate divided by 12 months
        val monthlyRate = interestRate / 12.0

        // Calculate the monthly payment
        return amount * monthlyRate / (1 - (1 + monthlyRate).pow(-getPeriodInMonth(year = termInYear, month = termInMonth).toDouble()))
    }

    fun calculateInterest(total: Double, amount: Double): Double {
        return total - amount
    }

    fun getPeriodInYear(year: Int, month: Int): Double{
        return ((month / 12) + year).toDouble()
    }

    fun getPeriodInMonth(year: Int, month: Int): Int{
        return (year * 12) + month
    }
}
