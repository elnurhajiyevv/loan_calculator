package  loan.calculator.save.viewmodel

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentActivity
import loan.calculator.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import loan.calculator.common.library.util.calculateAmortization
import loan.calculator.domain.entity.home.AmortizationModel
import loan.calculator.save.effect.SavePdfEffect
import loan.calculator.save.state.SavePdfState
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SavePdfViewModel @Inject constructor(
) : BaseViewModel<SavePdfState, SavePdfEffect>() {

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
