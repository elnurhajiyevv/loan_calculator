package  loan.calculator.save.viewmodel

import loan.calculator.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import loan.calculator.save.effect.AmortizationPageEffect
import loan.calculator.save.state.AmortizationPageState
import javax.inject.Inject

@HiltViewModel
class SavedAmortizationPageViewModel @Inject constructor(

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
