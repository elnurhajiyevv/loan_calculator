/*
 * Created by Elnur Hajiyev on on 8/6/22, 2:36 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.calculator.setting.effect

import loan.calculator.domain.entity.home.LanguageModel
import loan.calculator.setting.state.SettingPageState

open class SettingPageEffect {
    class OnAppVersion(val appVersion: String) : SettingPageEffect()

    class OnPackageName(val packageName: String) : SettingPageEffect()

    class ListOfLanguage(val list: List<LanguageModel>): SettingPageEffect()
}