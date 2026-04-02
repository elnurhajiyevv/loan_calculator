/*
 * Created by Elnur Hajiyev on on 8/6/22, 2:32 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.calculator.loan.view

import android.view.LayoutInflater
import android.view.ViewGroup
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
import dagger.hilt.android.AndroidEntryPoint
import loan.calculator.core.base.BaseFragment
import loan.calculator.loan.databinding.FragmentStatisticsPageBinding
import loan.calculator.loan.effect.StatisticsPageEffect
import loan.calculator.loan.state.StatisticsPageState
import loan.calculator.loan.viewmodel.StatisticsPageViewModel
import loan.calculator.uikit.R
import loan.calculator.uikit.util.getThemeColor

@AndroidEntryPoint
class StatisticsPageFragment :
    BaseFragment<StatisticsPageState, StatisticsPageEffect, StatisticsPageViewModel, FragmentStatisticsPageBinding>() {

    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentStatisticsPageBinding
        get() = FragmentStatisticsPageBinding::inflate

    override fun getViewModelClass() = StatisticsPageViewModel::class.java
    override fun getViewModelScope() = this

    var info = arrayListOf<String>()


    override val bindViews: FragmentStatisticsPageBinding.() -> Unit = {
        toolbar.setBackButtonVisibility(show = true)
    }

    private fun showPieChart(totalInterest: Float, totalPayment: Float) {

      //  paymentFormat(totalInterest, totalPayment)

        binding.chart.setUsePercentValues(true)
        binding.chart.setExtraOffsets(5f, 5f, 5f, 0f)
        binding.chart.isDrawHoleEnabled = true
        binding.chart.setHoleColor(resources.getColor(R.color.white_black))

        /*info.clear()
        info.add(resources.getString(R.string.loan_result_interest_header))
        info.add(resources.getString(R.string.total_repayment))*/

        val yvalues: MutableList<PieEntry> = ArrayList()
        yvalues.clear()
        yvalues.add(PieEntry(totalInterest, info[0]))
        yvalues.add(PieEntry(totalPayment - totalInterest, info[1]))
        val dataSet = PieDataSet(yvalues, "")
        dataSet.sliceSpace = 3f

        val xVals = ArrayList<String>()
        xVals.clear()
        xVals.add(info[0])
        xVals.add(info[1])
        val data = PieData(dataSet)

        val l = binding.chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.CENTER
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.xEntrySpace = 7f
        l.yEntrySpace = 5f
        l.yOffset = 0f
        l.textColor = resources.getColor(R.color.black_white)
        binding.chart.setEntryLabelColor(resources.getColor(R.color.black_white))

        data.setValueFormatter(PercentFormatter())
        data.setValueTextColor(resources.getColor(R.color.black_white))
        // data.setValueFormatter(new DefaultValueFormatter(0));
        // data.setValueFormatter(new DefaultValueFormatter(0));
        binding.chart.data = data
        binding.chart.setEntryLabelTextSize(13f)
        val colors = intArrayOf(
            0XFFB2C1DB.toInt(),
            getThemeColor(requireContext()),
        )
        dataSet.colors = ColorTemplate.createColors(colors)


        val d = Description()
        d.text = ""
        binding.chart.description = d
        binding.chart.transparentCircleRadius = 35f
        binding.chart.holeRadius = 35f
        data.setValueTextSize(13f)
        data.setValueTextColor(resources.getColor(R.color.black_white))
        binding.chart.animateXY(500, 500)
        binding.chart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {

            }

            override fun onNothingSelected() {

            }
        })
    }

}
