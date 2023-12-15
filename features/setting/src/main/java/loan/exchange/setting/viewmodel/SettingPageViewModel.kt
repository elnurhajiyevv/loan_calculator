package  loan.exchange.setting.viewmodel

import loan.exchange.core.base.BaseViewModel
import loan.exchange.setting.effect.SettingPageEffect
import loan.exchange.setting.state.SettingPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingPageViewModel @Inject constructor(
) : BaseViewModel<SettingPageState, SettingPageEffect>() {

}
