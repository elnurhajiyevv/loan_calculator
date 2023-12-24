package  loan.calculator.loan.viewmodel

import loan.calculator.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import loan.calculator.domain.entity.saved.GetSavedLoanModel
import loan.calculator.domain.usecase.savepage.InsertSaveLoanUseCase
import loan.calculator.loan.effect.SaveDialogEffect
import loan.calculator.loan.state.LoanPageState
import loan.calculator.loan.state.SaveDialogState
import javax.inject.Inject

@HiltViewModel
class SaveDialogViewModel @Inject constructor(
    private val insertSaveLoanUseCase: InsertSaveLoanUseCase,
) : BaseViewModel<SaveDialogState, SaveDialogEffect>() {

    fun insertSavedLoan(model: GetSavedLoanModel) {
        insertSaveLoanUseCase.launch(InsertSaveLoanUseCase.Params(model = model)) {
            onSuccess = {
                postEffect(SaveDialogEffect.InsertSavedLoan(model))
            }
        }
    }

}
