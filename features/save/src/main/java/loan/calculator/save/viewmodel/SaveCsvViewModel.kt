package loan.calculator.save.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import loan.calculator.common.library.util.calculateAmortization
import loan.calculator.core.base.BaseViewModel
import loan.calculator.domain.entity.home.AmortizationModel
import loan.calculator.domain.usecase.settingpage.GetCurrencyUseCase
import loan.calculator.save.effect.SaveCsvEffect
import loan.calculator.save.state.SaveCsvState
import javax.inject.Inject

@HiltViewModel
class SaveCsvViewModel @Inject constructor(
    private val getCurrencyUseCase: GetCurrencyUseCase
) : BaseViewModel<SaveCsvState, SaveCsvEffect>() {

    fun getCurrency() = getCurrencyUseCase.invoke(Unit)

    fun formulaAmortization(
        loanAmount: Double,
        termInMonths: Int,
        annualInterestRate: Double
    ): Array<AmortizationModel?> {
        return calculateAmortization(
            loanAmount = loanAmount,
            termInMonths = termInMonths,
            annualInterestRate = annualInterestRate
        )
    }
}
