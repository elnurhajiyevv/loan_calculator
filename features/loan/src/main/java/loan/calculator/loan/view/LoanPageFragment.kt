/*
 * Created by Elnur Hajiyev on on 8/6/22, 2:32 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.calculator.loan.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import loan.calculator.core.base.BaseFragment
import loan.calculator.loan.R
import loan.calculator.loan.databinding.FragmentLoanPageBinding
import loan.calculator.loan.effect.LoanPageEffect
import loan.calculator.loan.state.LoanPageState
import loan.calculator.loan.viewmodel.LoanPageViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoanPageFragment : BaseFragment<LoanPageState, LoanPageEffect, LoanPageViewModel, FragmentLoanPageBinding>() {


    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoanPageBinding
        get() = FragmentLoanPageBinding::inflate

    override fun getViewModelClass() = LoanPageViewModel::class.java
    override fun getViewModelScope() = this

    private var mInterstitialAd: InterstitialAd? = null

    override val bindViews: FragmentLoanPageBinding.() -> Unit = {
        toolbar.setBackButtonVisibility(show = false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var adRequest = AdRequest.Builder().build()
        InterstitialAd.load(requireContext(),getString(R.string.admodIdTest), adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("TAG", adError.toString())
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d("TAG", "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })


        /*binding.filter.setOnClickListenerDebounce {
            filterLoanBottomSheet {
                onApplyClicked = { amount, rate, period ->
                }
            }?.show(childFragmentManager, FilterLoanBottomSheet::class.java.canonicalName)
        }*/
    }


    override fun observeState(state: LoanPageState) {
        when(state){
            is LoanPageState.GetCurrency -> {}
        }
    }
}
