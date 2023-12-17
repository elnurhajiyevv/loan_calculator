package  loan.calculator.save.viewmodel

import loan.calculator.core.base.BaseViewModel
import loan.calculator.save.effect.SavePageEffect
import loan.calculator.save.state.SavePageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import loan.calculator.domain.usecase.savepage.ObserveSavedLoanUseCase
import loan.calculator.domain.usecase.savepage.RefreshSavedLoanUseCase
import javax.inject.Inject

@HiltViewModel
class SavePageViewModel @Inject constructor(
    private val observeSavedLoanUseCase: ObserveSavedLoanUseCase,
    private val refreshSavedLoanUseCase: RefreshSavedLoanUseCase,
) : BaseViewModel<SavePageState, SavePageEffect>() {

    private fun getCurrencies(){
        observeSavedLoanUseCase.execute(ObserveSavedLoanUseCase.Param())
            .filterNotNull()
            .onEach {
                //postState(SavePageState.GetSavedList()
            }
            .launchNoLoading()
        refreshSavedLoanUseCase.launch(RefreshSavedLoanUseCase.Param())
    }
}
