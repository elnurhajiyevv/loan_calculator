package  loan.exchange.loan.viewmodel

import loan.exchange.core.base.BaseViewModel
import loan.exchange.loan.effect.LoanPageEffect
import loan.exchange.loan.state.LoanPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import loan.exchange.domain.entity.home.CurrencyModel
import loan.exchange.domain.usecase.homepage.server.ObserveCurrenciesUseCase
import loan.exchange.domain.usecase.homepage.server.RefreshCurrenciesUseCase
import javax.inject.Inject

@HiltViewModel
class LoanPageViewModel @Inject constructor(
    private val observeCurrencyUseCase: ObserveCurrenciesUseCase,
    private val refreshCurrencyUseCase: RefreshCurrenciesUseCase,
) : BaseViewModel<LoanPageState, LoanPageEffect>() {

    var availableCurrency = arrayListOf<CurrencyModel>()

    var selectedCurrency = ""

    fun getCurrency(){
        observeCurrencyUseCase.execute(ObserveCurrenciesUseCase.Param())
            .filterNotNull()
            .onEach {
                availableCurrency.clear()
                it.result?.forEach {
                    availableCurrency.add(CurrencyModel(name = it.key, description = it.value))
                }
                postState(LoanPageState.GetCurrency((availableCurrency)))
            }
            .launchNoLoading()
        refreshCurrencyUseCase.launch(RefreshCurrenciesUseCase.Param())
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
