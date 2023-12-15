package loan.exchange.currency

import loan.exchange.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

) : BaseViewModel<Unit, MainEvent>() {

}
sealed class MainEvent {
}