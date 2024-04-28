/*
 * Created by Elnur Hajiyev on on 8/6/22, 2:32 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.calculator.setting.view

import android.annotation.TargetApi
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint
import loan.calculator.common.library.util.marketLink
import loan.calculator.common.library.util.webLink
import loan.calculator.core.base.BaseFragment
import loan.calculator.domain.entity.home.LanguageModel
import loan.calculator.setting.R
import loan.calculator.setting.bottomsheet.BugReportBottomSheet
import loan.calculator.setting.bottomsheet.LanguageMenuBottomSheet
import loan.calculator.setting.bottomsheet.bugReportBottomSheetBottomSheet
import loan.calculator.setting.bottomsheet.languageMenuBottomSheet
import loan.calculator.setting.databinding.FragmentSettingPageBinding
import loan.calculator.setting.effect.SettingPageEffect
import loan.calculator.setting.state.SettingPageState
import loan.calculator.setting.viewmodel.SettingPageViewModel
import loan.calculator.uikit.ads.NativeTemplateStyle
import loan.calculator.uikit.extension.getImageResource
import java.util.Locale
import kotlin.coroutines.coroutineContext


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
            viewmodel.getLanguageList()
        }
        rateUs.setOnClickListener {
            // get app package name
            viewmodel.getPackageName()
        }

        bugReport.setOnClickListener {
            openBugReportBottomModule()
        }

        // get app language and update UI
        getAndUpdateLanguage()
    }

    private fun getAndUpdateLanguage() {
        setLanguage(viewmodel.getLanguage())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MobileAds.initialize(requireContext())
        val adLoader = AdLoader.Builder(requireContext(), "ca-app-pub-3940256099942544/2247696110")
            .forNativeAd { nativeAd ->
                val styles: NativeTemplateStyle =
                    NativeTemplateStyle.Builder().build()

                binding.myTemplate.setStyles(styles)
                binding.myTemplate.setNativeAd(nativeAd)
            }
            .build()

        adLoader.loadAd(AdRequest.Builder().build())
    }

    override fun observeEffect(effect: SettingPageEffect) {
        when(effect){
            is SettingPageEffect.OnAppVersion -> {
                binding.appVersion.text = getString(R.string.app_version, effect.appVersion)
            }
            is SettingPageEffect.OnPackageName -> {
                navigateToRateUs(effect.packageName)
            }
            is SettingPageEffect.ListOfLanguage -> {
                openLanguageBottomModule(effect.list)
            }
        }
    }

    private fun navigateToRateUs(packageName: String) {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("$marketLink$packageName")
                )
            )
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("$webLink$packageName")
                )
            )
        }
    }

    private fun openLanguageBottomModule(list: List<LanguageModel>){
        languageMenuBottomSheet {
            itemList = list
            onItemsSelected = {
                // update language when selected
                updateSelectedLanguage(it)
            }
        }?.show(childFragmentManager, LanguageMenuBottomSheet::class.java.canonicalName)
    }

    private fun openBugReportBottomModule(){
        bugReportBottomSheetBottomSheet {
            onItemsSelected = {
                // update
            }
        }?.show(childFragmentManager, BugReportBottomSheet::class.java.canonicalName)
    }

    private fun updateSelectedLanguage(language: LanguageModel) {
        viewmodel.setLanguage(language)
        setLanguage(language)
        changeAppContext()
    }

    private fun setLanguage(language: LanguageModel){
        binding.changeLanguageImage.setImageResource(language.name.getImageResource())
        binding.changeLanguageText.text = language.nationalName
    }
    private fun changeAppContext() {
        requireActivity().recreate()
    }
}