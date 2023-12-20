/*
 * Created by Elnur Hajiyev on on 8/6/22, 2:32 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.calculator.setting.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import loan.calculator.core.base.BaseFragment
import loan.calculator.setting.databinding.FragmentSettingPageBinding
import loan.calculator.setting.effect.SettingPageEffect
import loan.calculator.setting.state.SettingPageState
import loan.calculator.setting.viewmodel.SettingPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import loan.calculator.core.tools.NavigationCommand
import loan.calculator.domain.entity.enums.UNIT_TYPE
import loan.calculator.domain.entity.home.LanguageModel
import loan.calculator.domain.entity.unit.UnitModel
import loan.calculator.setting.R
import loan.calculator.setting.adapter.UnitAdapter
import loan.calculator.setting.bottomsheet.LanguageMenuBottomSheet
import loan.calculator.setting.bottomsheet.languageMenuBottomSheet

@AndroidEntryPoint
class SettingPageFragment : BaseFragment<SettingPageState, SettingPageEffect, SettingPageViewModel, FragmentSettingPageBinding>() {

    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSettingPageBinding
        get() = FragmentSettingPageBinding::inflate

    override fun getViewModelClass() = SettingPageViewModel::class.java
    override fun getViewModelScope() = this


    override val bindViews: FragmentSettingPageBinding.() -> Unit = {

        // get app version
        viewmodel.getCurrentApplicationVersionName()

        // disable toolbar back button
        toolbar.setBackButtonVisibility(show = false)

        switchOnOff.isChecked = viewmodel.getLightTheme()
        // handle language change
        switchOnOff.setOnCheckedChangeListener { buttonView, isChecked ->
            AppCompatDelegate.setDefaultNightMode(if(isChecked)AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES)
            viewmodel.setLightTheme(isChecked)
        }
        changeLanguage.setOnClickListener {
            // get list of available languages
            // viewmodel.getLanguage()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun observeEffect(effect: SettingPageEffect) {
        when(effect){
            is SettingPageEffect.OnAppVersion -> {
                binding.appVersion.text = getString(R.string.app_version, effect.appVersion)
            }
        }
    }

    override fun observeState(state: SettingPageState) {
        when(state){
            is SettingPageState.ListOfLanguage -> openLanguageBottomModule(state.list)
        }
    }

    private fun openLanguageBottomModule(list: List<LanguageModel>){
        languageMenuBottomSheet {
            itemList = list
            onItemsSelected = {
                // update language when selected
            }
        }?.show(childFragmentManager, LanguageMenuBottomSheet::class.java.canonicalName)
    }

}