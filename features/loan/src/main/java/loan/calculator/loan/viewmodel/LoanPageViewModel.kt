package  loan.calculator.loan.viewmodel

import loan.calculator.core.base.BaseViewModel
import loan.calculator.loan.effect.LoanPageEffect
import loan.calculator.loan.state.LoanPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import loan.calculator.domain.usecase.settingpage.GetScreenShotUseCase
import loan.calculator.domain.usecase.settingpage.GetShowCaseUseCase
import loan.calculator.domain.usecase.settingpage.SetScreenShotUseCase
import loan.calculator.domain.usecase.settingpage.SetShowCaseUseCase
import loan.calculator.domain.util.SELECT_PART
import javax.inject.Inject

@HiltViewModel
class LoanPageViewModel @Inject constructor(
    private val getShowCaseUseCase: GetShowCaseUseCase,
    private val setShowCaseUseCase: SetShowCaseUseCase,
    ) : BaseViewModel<LoanPageState, LoanPageEffect>() {

    fun getShowCase() = getShowCaseUseCase.invoke(Unit)

    fun setShowCase(id:Int){
        setShowCaseUseCase.invoke(SetShowCaseUseCase.Params(id = id))
    }

    var setSelection = SELECT_PART.PAYMENT

    var totalInterest = 6618.55
    var totalPayment = 106618.55


    var selectedLoanAmount = 100000.0F
    var selectedLoanPeriodYear = 1
    var selectedLoanPeriodMonth = 0
    var selectedLoanRate = 12
    var selectedLoanPayment = 8884.87

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




    fun getPeriodInYear(year: Int, month: Int): Double{
        return ((month / 12) + year).toDouble()
    }

    fun convertMonthToYear(month: Int): Int{
        return month / 12
    }

    fun convertedMonth(month: Int): Int{
        return month % 12
    }

    fun getPeriodInMonth(year: Int, month: Int): Int{
        return (year * 12) + month
    }
}
