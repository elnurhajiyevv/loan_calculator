/*
 * Created by Elnur Hajiyev on on 8/6/22, 2:32 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.calculator.loan.view

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dagger.hilt.android.AndroidEntryPoint
import loan.calculator.common.extensions.setOnClickListenerDebounce
import loan.calculator.core.base.BaseFragment
import loan.calculator.core.tools.NavigationCommand
import loan.calculator.loan.R
import loan.calculator.loan.databinding.FragmentLoanPageBinding
import loan.calculator.loan.effect.LoanPageEffect
import loan.calculator.loan.state.LoanPageState
import loan.calculator.loan.viewmodel.LoanPageViewModel
import loan.calculator.uikit.extension.enableSumFormatting


@AndroidEntryPoint
class LoanPageFragment : BaseFragment<LoanPageState, LoanPageEffect, LoanPageViewModel, FragmentLoanPageBinding>() {

    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoanPageBinding
        get() = FragmentLoanPageBinding::inflate

    override fun getViewModelClass() = LoanPageViewModel::class.java
    override fun getViewModelScope() = this

    private var mInterstitialAd: InterstitialAd? = null

    var info = arrayOf("Total interest", "Total repayment")

    override val bindViews: FragmentLoanPageBinding.() -> Unit = {
        toolbar.setBackButtonVisibility(show = false)

        toolbar.setToolbarLeftActionClick {
            resetValues()
        }

        toolbar.setToolbarRightActionClick {
            saveValues()
        }

        type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(arg0: AdapterView<*>?, arg1: View?, position: Int, id: Long) {
                // TODO Auto-generated method stub
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {
                // TODO Auto-generated method stub
            }
        }

        loanAmountEdittext.enableSumFormatting()
        loanPaymentEdittext.enableSumFormatting()

        loanAmountPart.setOnClickListenerDebounce { selectPart(SELECT_PART.AMOUNT) }

        loanRatePart.setOnClickListenerDebounce { selectPart(SELECT_PART.RATE) }

        loanPeriodPart.setOnClickListenerDebounce { selectPart(SELECT_PART.PERIOD) }

        loanPaymentPart.setOnClickListenerDebounce { selectPart(SELECT_PART.PAYMENT) }

        applyButton.setOnClickListenerDebounce {
            viewmodel.navigate(
                NavigationCommand.To(
                    LoanPageFragmentDirections.actionLoanPageFragmentToAmortizationFragment()
                )
            )
        }

        loanAmountEdittext.setOnClickListenerDebounce {

        }

        loanRateEdittext.setOnClickListenerDebounce {

        }

        loanPaymentEdittext.setOnClickListenerDebounce {

        }

        loanPaymentEdittext.setOnClickListenerDebounce {

        }

    }

    private fun saveValues() {

    }

    fun showPieChart(){
        binding.chart.setUsePercentValues(true)
        binding.chart.setExtraOffsets(25f, 5f, 25f, 0f)
        binding.chart.isDrawHoleEnabled = true
        binding.chart.setHoleColor(Color.WHITE)

        val yvalues: MutableList<PieEntry> = ArrayList()
        yvalues.add(PieEntry(3600.28f, info[0]))
        yvalues.add(PieEntry(10360.28f, info[1]))
        val dataSet = PieDataSet(yvalues, "")
        dataSet.sliceSpace = 3f

        val xVals = ArrayList<String>()
        xVals.add(info[0])
        xVals.add(info[1])
        val data = PieData(dataSet)

        data.setValueFormatter(PercentFormatter())
        // data.setValueFormatter(new DefaultValueFormatter(0));
        // data.setValueFormatter(new DefaultValueFormatter(0));
        binding.chart.data = data
        binding.chart.setEntryLabelTextSize(13f)
        val colors = intArrayOf(
            Color.GRAY,
            Color.GREEN
        )
        dataSet.colors = ColorTemplate.createColors(colors)

        val d = Description()
        d.textSize = 18f
        d.setPosition(65f, 50f)
        d.textAlign = Paint.Align.LEFT
        d.text = ""
        binding.chart.description = d
        binding.chart.transparentCircleRadius = 35f
        binding.chart.holeRadius = 35f
        data.setValueTextSize(13f)
        data.setValueTextColor(Color.WHITE)
        binding.chart.animateXY(1500, 1500)
        binding.chart.setOnChartValueSelectedListener(object :OnChartValueSelectedListener{
            override fun onValueSelected(e: Entry?, h: Highlight?) {

            }

            override fun onNothingSelected() {

            }
        })
    }

    private fun resetValues() {
        binding.loanAmountEdittext.text.clear()
        binding.loanRateEdittext.text.clear()
        binding.loanYearEdittext.text.clear()
        binding.loanMonthEdittext.text.clear()
        binding.loanPaymentEdittext.text.clear()
    }

    private fun selectPart(type: SELECT_PART) {
        resetSelection()
        when(type){
            SELECT_PART.AMOUNT -> {
                binding.loanAmount.setBackgroundResource(R.drawable.radius_10_blue)
                binding.loanAmountPart.setBackgroundColor(resources.getColor(R.color.light_blue_100))
            }
            SELECT_PART.PERIOD -> {
                binding.loanPeriod.setBackgroundResource(R.drawable.radius_10_blue)
                binding.loanPeriodPart.setBackgroundColor(resources.getColor(R.color.light_blue_100))
            }
            SELECT_PART.RATE -> {
                binding.loanRate.setBackgroundResource(R.drawable.radius_10_blue)
                binding.loanRatePart.setBackgroundColor(resources.getColor(R.color.light_blue_100))
            }
            SELECT_PART.PAYMENT -> {
                binding.loanPayment.setBackgroundResource(R.drawable.radius_10_blue)
                binding.loanPaymentPart.setBackgroundColor(resources.getColor(R.color.light_blue_100))
            }
        }
    }

    private fun resetSelection() {
        binding.loanPayment.setBackgroundResource(R.drawable.radius_10_white)
        binding.loanPaymentPart.setBackgroundColor(resources.getColor(R.color.color_gray_four))
        binding.loanAmount.setBackgroundResource(R.drawable.radius_10_white)
        binding.loanAmountPart.setBackgroundColor(resources.getColor(R.color.color_gray_four))
        binding.loanRate.setBackgroundResource(R.drawable.radius_10_white)
        binding.loanRatePart.setBackgroundColor(resources.getColor(R.color.color_gray_four))
        binding.loanPeriod.setBackgroundResource(R.drawable.radius_10_white)
        binding.loanPeriodPart.setBackgroundColor(resources.getColor(R.color.color_gray_four))
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


        showPieChart()
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

    enum class SELECT_PART(type: String){
        AMOUNT("amount"),PERIOD("period"),RATE("rate"),PAYMENT("payment")
    }
}
