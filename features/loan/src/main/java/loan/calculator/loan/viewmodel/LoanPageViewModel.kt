package  loan.calculator.loan.viewmodel

import loan.calculator.core.base.BaseViewModel
import loan.calculator.loan.effect.LoanPageEffect
import loan.calculator.loan.state.LoanPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import loan.calculator.domain.entity.home.SavedModel
import loan.calculator.domain.usecase.savepage.ObserveSavedLoanUseCase
import loan.calculator.domain.usecase.savepage.RefreshSavedLoanUseCase
import javax.inject.Inject

@HiltViewModel
class LoanPageViewModel @Inject constructor(
    private val observeCurrencyUseCase: ObserveSavedLoanUseCase,
    private val refreshCurrencyUseCase: RefreshSavedLoanUseCase,
) : BaseViewModel<LoanPageState, LoanPageEffect>() {

    var availableCurrency = arrayListOf<SavedModel>()

    var selectedCurrency = ""

    fun getCurrency(){
        observeCurrencyUseCase.execute(ObserveSavedLoanUseCase.Param())
            .filterNotNull()
            .onEach {
                availableCurrency.clear()
               /* it.result?.forEach {
                    availableCurrency.add(SavedModel(name = it.key, description = it.value))
                }*/
                postState(LoanPageState.GetCurrency((availableCurrency)))
            }
            .launchNoLoading()
        refreshCurrencyUseCase.launch(RefreshSavedLoanUseCase.Param())
    }

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
