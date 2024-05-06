package  loan.calculator.save.viewmodel

import loan.calculator.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import loan.calculator.save.effect.SaveCsvEffect
import loan.calculator.save.state.SaveCsvState
import javax.inject.Inject

@HiltViewModel
class SaveCsvViewModel @Inject constructor(
) : BaseViewModel<SaveCsvState, SaveCsvEffect>() {

}
