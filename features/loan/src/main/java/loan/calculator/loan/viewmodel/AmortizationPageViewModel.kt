package  loan.calculator.loan.viewmodel

import loan.calculator.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import loan.calculator.loan.effect.AmortizationPageEffect
import loan.calculator.loan.state.AmortizationPageState
import javax.inject.Inject

@HiltViewModel
class AmortizationPageViewModel @Inject constructor(

) : BaseViewModel<AmortizationPageState, AmortizationPageEffect>() {

    fun calculateMonthly(total: Double, period: Double): Double {
        return total/period
    }

    fun calculateTotal(amount: Double, rate: Double, period: Double): Double {
        var partTotal = ((((rate * 57) / 100) * amount) / 100) * ((period / 12))
        return partTotal + amount
    }

    fun calculateInterest(total: Double, amount: Double): Double {
        return total - amount
    }
}
