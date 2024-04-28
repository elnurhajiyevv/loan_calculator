package  loan.calculator.save.viewmodel

import loan.calculator.core.base.BaseViewModel
import loan.calculator.save.effect.SavePageEffect
import loan.calculator.save.state.SavePageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import loan.calculator.domain.entity.saved.GetSavedLoanModel
import loan.calculator.domain.usecase.savepage.DeleteSavedLoanUseCase
import loan.calculator.domain.usecase.savepage.GetSavedLoansUseCase
import javax.inject.Inject

@HiltViewModel
class SavePageViewModel @Inject constructor(
    private val getSavedLoansUseCase: GetSavedLoansUseCase,
    private val deleteSavedLoanUseCase: DeleteSavedLoanUseCase,
) : BaseViewModel<SavePageState, SavePageEffect>() {

    var list: List<GetSavedLoanModel> = arrayListOf()

    lateinit var selectedItem: GetSavedLoanModel
    fun getSavedLoans(){
        launchAll(loadingHandle = {}) {
            getSavedLoansUseCase.execute(Unit)
                .filterNotNull()
                .collectLatest { savedLoans ->
                    postState(SavePageState.GetSavedList(savedList = savedLoans))
                }
        }
    }

    fun deleteSavedLoan(name: String) {
        deleteSavedLoanUseCase.launch(DeleteSavedLoanUseCase.Params(name)) {
            onSuccess = {
                postState(SavePageState.DeleteSaveLoan)
            }
        }
    }
}
