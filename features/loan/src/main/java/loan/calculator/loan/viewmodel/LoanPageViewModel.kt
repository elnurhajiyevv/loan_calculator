package  loan.calculator.loan.viewmodel

import loan.calculator.core.base.BaseViewModel
import loan.calculator.loan.effect.LoanPageEffect
import loan.calculator.loan.state.LoanPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import loan.calculator.loan.view.LoanPageFragment
import javax.inject.Inject

@HiltViewModel
class LoanPageViewModel @Inject constructor(
) : BaseViewModel<LoanPageState, LoanPageEffect>() {

    var setSelection = LoanPageFragment.SELECT_PART.PAYMENT

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

    fun calculateTotal(amount: Double, rate: Double, period: Double): Double {
        var partTotal = ((((rate * 57) / 100) * amount) / 100) * ((period / 12))
        return partTotal + amount
    }

    fun calculateInterest(total: Double, amount: Double): Double {
        return total - amount
    }
}
