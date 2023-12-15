package  loan.exchange.home.viewmodel

import loan.exchange.core.base.BaseViewModel
import loan.exchange.domain.entity.home.CurrencyModel
import loan.exchange.home.effect.HomePageEffect
import loan.exchange.home.state.HomePageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import loan.exchange.domain.entity.home.response.GetCurrenciesResponseModel
import loan.exchange.domain.entity.home.response.LatestRatesResponseModel
import loan.exchange.domain.usecase.homepage.amount.GetSelectedAmountUseCase
import loan.exchange.domain.usecase.homepage.amount.GetSelectedConvertedUseCase
import loan.exchange.domain.usecase.homepage.amount.GetSelectedRateUseCase
import loan.exchange.domain.usecase.homepage.amount.SetLastSelectedAmountUseCase
import loan.exchange.domain.usecase.homepage.amount.SetLastSelectedConvertedUseCase
import loan.exchange.domain.usecase.homepage.amount.SetLastSelectedRateUseCase
import loan.exchange.domain.usecase.homepage.server.GetLatestExchangeRatesUseCase
import loan.exchange.domain.usecase.homepage.server.ObserveCurrenciesUseCase
import loan.exchange.domain.usecase.homepage.server.RefreshCurrenciesUseCase
import loan.exchange.home.view.HomePageFragment
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val observeCurrenciesUseCase: ObserveCurrenciesUseCase,
    private val refreshCurrenciesUseCase: RefreshCurrenciesUseCase,
    private val getLatestExchangeRatesUseCase: GetLatestExchangeRatesUseCase,
    private val getSelectedAmountUseCase: GetSelectedAmountUseCase,
    private val setLastSelectedAmountUseCase: SetLastSelectedAmountUseCase,
    private val getSelectedConvertedUseCase: GetSelectedConvertedUseCase,
    private val setLastSelectedConvertedUseCase: SetLastSelectedConvertedUseCase,
    private val getSelectedRateUseCase: GetSelectedRateUseCase,
    private val setLastSelectedRateUseCase: SetLastSelectedRateUseCase,
    ) : BaseViewModel<HomePageState, HomePageEffect>() {

    var availableCurrency = arrayListOf<CurrencyModel>()
    var availableCurrencyWithRate = arrayListOf<CurrencyModel>()

   /* var selectedAmount = ""
    var convertedSelectedAmount = ""
    var rate = 1.0*/

    init {
        getCurrencies()
    }

    fun getSelectedAmount() = getSelectedAmountUseCase.invoke(Unit)

    fun getSelectedConverted() = getSelectedConvertedUseCase.invoke(Unit)

    fun getSelectedRate() = getSelectedRateUseCase.invoke(Unit)

    fun setLastSelectedAmount(lastSelectedAmountUseCase:String){
        setLastSelectedAmountUseCase.invoke(SetLastSelectedAmountUseCase.Params(lastSelectedAmountUseCase))
    }

    fun setLastSelectedConverted(lastSelectedConvertedUseCase:String){
        setLastSelectedConvertedUseCase.invoke(SetLastSelectedConvertedUseCase.Params(lastSelectedConvertedUseCase))
    }

    fun setLastSelectedRate(rate:Float){
        setLastSelectedRateUseCase.invoke(SetLastSelectedRateUseCase.Params(rate))
    }

    fun latestExchangeRates(type: HomePageFragment.SELECTION_TYPE, base: String) {
        launchAll {
            getLatestExchangeRatesUseCase.launch(GetLatestExchangeRatesUseCase.Param(base = base)) {
                onSuccess = {
                    updateCurrenciesWithRate(type,it)
                }
                onError = {
                    postState(HomePageState.GetLatestExchangeRatesErrorResult)
                }
            }
        }
    }

    private fun updateCurrenciesWithRate(type: HomePageFragment.SELECTION_TYPE, response: LatestRatesResponseModel) {
        if(type == HomePageFragment.SELECTION_TYPE.AMOUNT){
            availableCurrencyWithRate.clear()
            response.result?.forEach {
                availableCurrencyWithRate.add(CurrencyModel(name = it.key, description = it.value))
            }
        }
        postState(HomePageState.GetLatestExchangeRates(type,response))
    }

    //postState(HomePageState.GetCurrencyResult)

    private fun getCurrencies(){
        observeCurrenciesUseCase.execute(ObserveCurrenciesUseCase.Param())
            .filterNotNull()
            .onEach {
                updateCurrencies(it)
            }
            .launchNoLoading()
        refreshCurrenciesUseCase.launch(RefreshCurrenciesUseCase.Param())
    }

    private fun updateCurrencies(response: GetCurrenciesResponseModel) {
        availableCurrency.clear()
        response.result?.forEach {
            availableCurrency.add(CurrencyModel(name = it.key, description = it.value))
        }
    }
}
