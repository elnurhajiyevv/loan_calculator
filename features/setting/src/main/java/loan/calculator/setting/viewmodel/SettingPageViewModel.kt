package  loan.calculator.setting.viewmodel

import loan.calculator.core.base.BaseViewModel
import loan.calculator.setting.effect.SettingPageEffect
import loan.calculator.setting.state.SettingPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingPageViewModel @Inject constructor(
) : BaseViewModel<SettingPageState, SettingPageEffect>() {

}
