package loan.calculator

import loan.calculator.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

) : BaseViewModel<Unit, MainEvent>() {

}
sealed class MainEvent {
}