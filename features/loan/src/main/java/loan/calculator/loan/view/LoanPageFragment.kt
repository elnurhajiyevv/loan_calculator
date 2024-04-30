/*
 * Created by Elnur Hajiyev on on 8/6/22, 2:32 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.calculator.loan.view

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
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
import com.google.android.gms.ads.interstitial.InterstitialAd
import dagger.hilt.android.AndroidEntryPoint
import loan.calculator.common.extensions.asFormattedDateWithDot
import loan.calculator.common.extensions.getDoubleValue
import loan.calculator.common.extensions.setOnClickListenerDebounce
import loan.calculator.common.library.util.calculateAmortization
import loan.calculator.common.library.util.calculateMonthlyPayment
import loan.calculator.core.base.BaseFragment
import loan.calculator.core.tools.NavigationCommand
import loan.calculator.domain.entity.home.Loan
import loan.calculator.domain.entity.home.LoanInfo
import loan.calculator.domain.util.SELECT_PART
import loan.calculator.domain.util.calculatePayment
import loan.calculator.loan.R
import loan.calculator.loan.databinding.FragmentLoanPageBinding
import loan.calculator.loan.effect.LoanPageEffect
import loan.calculator.loan.state.LoanPageState
import loan.calculator.loan.viewmodel.LoanPageViewModel
import loan.calculator.uikit.edittext.InputFilterMinMax
import loan.calculator.uikit.extension.enableSumFormatting
import loan.calculator.uikit.util.calculatePaidOff
import loan.calculator.uikit.util.disableSelection
import loan.calculator.uikit.util.getValor
import loan.calculator.uikit.util.returnValueIfNull
import loan.calculator.uikit.util.setBackgroundColor
import loan.calculator.uikit.util.setBackgroundResources
import loan.calculator.uikit.util.setImageResources
import java.util.Date


@AndroidEntryPoint
class LoanPageFragment : BaseFragment<LoanPageState, LoanPageEffect, LoanPageViewModel, FragmentLoanPageBinding>() {

    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoanPageBinding
        get() = FragmentLoanPageBinding::inflate

    override fun getViewModelClass() = LoanPageViewModel::class.java
    override fun getViewModelScope() = this

    private var mInterstitialAd: InterstitialAd? = null

    var info = arrayOf("Total interest", "Total repayment")

    var loanAmountFocus = false
    var loanPeriodYearFocus = false
    var loanPeriodMonthFocus = false
    var loanRateFocus = false
    var loanPaymentFocus = false

    override val bindViews: FragmentLoanPageBinding.() -> Unit = {
        toolbar.setBackButtonVisibility(show = false)

        toolbar.setToolbarLeftActionClick {
            defaultSelection()
        }

        toolbar.setToolbarRightActionClick {
            saveValues()
        }

        type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(arg0: AdapterView<*>?, arg1: View?, position: Int, id: Long) {

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
            var termInMonth = viewmodel.getPeriodInMonth(
                returnValueIfNull(binding.loanYearEdittext).toInt(),
                returnValueIfNull(binding.loanMonthEdittext).toInt()
            )
            viewmodel.navigate(
                NavigationCommand.To(
                    LoanPageFragmentDirections.actionLoanPageFragmentToAmortizationFragment(
                        loanInfo = LoanInfo(
                            name = "Loan Calculator",
                            backgroundColor = 0,
                            startDate = Date().asFormattedDateWithDot(),
                            paidOff = calculatePaidOff(
                                termInMonth, Date()
                            ),
                            loanAmount = returnValueIfNull(binding.loanAmountEdittext).getDoubleValue(),
                            interestRate = returnValueIfNull(binding.loanRateEdittext).getDoubleValue(),
                            frequency = type.selectedItem.toString(),
                            totalRepayment = returnValueIfNull(binding.loanPaymentEdittext),
                            termInMonth = termInMonth
                        )
                    )
                )
            )
        }

        loanMonthEdittext.filters = arrayOf<InputFilter>(InputFilterMinMax(1, 11))
        loanYearEdittext.filters = arrayOf<InputFilter>(InputFilterMinMax(1, 99))

        loanAmountEdittext.onFocusChangeListener = View.OnFocusChangeListener { _, b -> loanAmountFocus = b }
        loanAmountEdittext.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(loanAmountFocus){
                    try {
                        var selectionType = viewmodel.setSelection
                        var termInMonth = viewmodel.getPeriodInMonth (
                            month = returnValueIfNull(loanMonthEdittext).toInt(),
                            year = returnValueIfNull(loanYearEdittext).toInt()
                        )
                        setCalculatedValue(
                            type = selectionType,
                            value = calculatePayment(
                                amount = getValor(s),
                                termInMonth = termInMonth,
                                interestRate = getValor(returnValueIfNull(loanRateEdittext)),
                                payment = 0.0,
                                type = selectionType,
                                frequency = type.selectedItem.toString()
                            )
                        )
                    } catch (e: Exception){
                        e.printStackTrace()
                    }
                }

            }
        })

        loanRateEdittext.onFocusChangeListener = View.OnFocusChangeListener { _, b -> loanRateFocus = b }
        loanRateEdittext.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(loanRateFocus){
                    try {
                        var selectionType = viewmodel.setSelection
                        var termInMonth = viewmodel.getPeriodInMonth (
                            month = returnValueIfNull(loanMonthEdittext).toInt(),
                            year = returnValueIfNull(loanYearEdittext).toInt()
                        )
                        setCalculatedValue(
                            type = selectionType,
                            value = calculatePayment(
                                amount = getValor(returnValueIfNull(loanAmountEdittext)),
                                termInMonth = termInMonth,
                                interestRate = getValor(s),
                                payment = 0.0,
                                type = selectionType,
                                frequency = type.selectedItem.toString()
                            )
                        )
                    }catch (e: Exception){
                        e.printStackTrace()
                        loanRateEdittext.setText("")
                    }
                }
            }
        })

        loanPaymentEdittext.onFocusChangeListener = View.OnFocusChangeListener { _, b -> loanPaymentFocus = b }
        loanPaymentEdittext.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(loanPaymentFocus){
                    try {
                        // getValor(s)
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            }
        })

        loanMonthEdittext.onFocusChangeListener = View.OnFocusChangeListener { _, b -> loanPeriodMonthFocus = b }
        loanMonthEdittext.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(loanPeriodMonthFocus){
                    try {
                        var selectionType = viewmodel.setSelection
                        var termInMonth = viewmodel.getPeriodInMonth (
                            month = getValor(s).toInt(),
                            year = returnValueIfNull(loanYearEdittext).toInt()
                        )
                        setCalculatedValue(
                            type = selectionType,
                            value = calculatePayment(
                                amount = getValor(returnValueIfNull(loanAmountEdittext)),
                                termInMonth = termInMonth,
                                interestRate = getValor(returnValueIfNull(loanRateEdittext)),
                                payment = 0.0,
                                type = selectionType,
                                frequency = type.selectedItem.toString()
                            )
                        )
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            }
        })

        loanYearEdittext.onFocusChangeListener = View.OnFocusChangeListener { _, b -> loanPeriodYearFocus = b }
        loanYearEdittext.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(loanPeriodYearFocus){
                    try {
                        var selectionType = viewmodel.setSelection
                        var termInMonth = viewmodel.getPeriodInMonth (
                            month = returnValueIfNull(loanMonthEdittext).toInt(),
                            year = getValor(s).toInt()
                        )
                        setCalculatedValue(
                            type = selectionType,
                            value = calculatePayment(
                                amount = getValor(returnValueIfNull(loanAmountEdittext)),
                                termInMonth = termInMonth,
                                interestRate = getValor(returnValueIfNull(loanRateEdittext)),
                                payment = 0.0,
                                type = selectionType,
                                frequency = type.selectedItem.toString()
                            )
                        )
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    fun setCalculatedValue(type: SELECT_PART, value: Double) {
        when(type){
            SELECT_PART.AMOUNT -> binding.loanAmountEdittext.setText(value.toString())
            SELECT_PART.PERIOD -> {
                binding.loanYearEdittext.setText(viewmodel.convertMonthToYear(value.toInt()))
                binding.loanMonthEdittext.setText(viewmodel.convertedMonth(value.toInt()))
            }
            SELECT_PART.RATE -> binding.loanRateEdittext.setText(value.toString())
            SELECT_PART.PAYMENT -> binding.loanPaymentEdittext.setText(value.toString())
        }

        var loanModel = Loan(
            loanAmount = returnValueIfNull(binding.loanAmountEdittext).getDoubleValue(),
            termInMonths = viewmodel.getPeriodInMonth(
                returnValueIfNull(binding.loanYearEdittext).toInt(),
                returnValueIfNull(binding.loanMonthEdittext).toInt()
            ),
            annualInterestRate = returnValueIfNull(binding.loanRateEdittext).getDoubleValue(),
            downPayment = 0.0,
            tradeInValue = 0.0,
            salesTaxRate = 0.0,
            fees = 0.0
        )
        viewmodel.totalInterest = loanModel.totalLoanInterest
        viewmodel.totalPayment = loanModel.totalLoanPayments

        showPieChart(
            totalInterest = viewmodel.totalInterest.toFloat(),
            totalPayment = viewmodel.totalPayment.toFloat()
        )

    }

    private fun saveValues() {
        var termInMonth = viewmodel.getPeriodInMonth(
            returnValueIfNull(binding.loanYearEdittext).toInt(),
            returnValueIfNull(binding.loanMonthEdittext).toInt()
        )
        val saveDialog = SaveDialog
        saveDialog.build(
            amount = returnValueIfNull(binding.loanAmountEdittext),
            period = viewmodel.getPeriodInMonth(
                returnValueIfNull(binding.loanYearEdittext).toInt(),
                returnValueIfNull(binding.loanMonthEdittext).toInt()
            ).toString(),
            rate = returnValueIfNull(binding.loanRateEdittext),
            payment = returnValueIfNull(binding.loanPaymentEdittext),
            frequency = binding.type.selectedItem.toString(),
            termInMonth = termInMonth
        ).show(parentFragmentManager,"saveDialog")
    }

    private fun showPieChart(totalInterest: Float, totalPayment: Float){

        binding.totalInterestValue.text = "${String.format("%.2f", totalInterest).replace(",",".").toFloat()}"
        binding.totalRepaymentValue.text = "${String.format("%.2f", totalPayment).replace(",",".").toFloat()}"
        binding.totalInterestValue.enableSumFormatting()
        binding.totalRepaymentValue.enableSumFormatting()

        binding.chart.setUsePercentValues(true)
        binding.chart.setExtraOffsets(5f, 5f, 5f, 0f)
        binding.chart.isDrawHoleEnabled = true
        binding.chart.setHoleColor(resources.getColor(R.color.white_black))

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
        binding.chart.setEntryLabelColor(resources.getColor(R.color.black_white))

        data.setValueFormatter(PercentFormatter())
        // data.setValueFormatter(new DefaultValueFormatter(0));
        // data.setValueFormatter(new DefaultValueFormatter(0));
        binding.chart.data = data
        binding.chart.setEntryLabelTextSize(13f)
        val colors = intArrayOf(
            0xFFB2C1DB.toInt(),
            0xFF7DD16A.toInt()
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
                selection(binding.loanAmount,binding.loanAmountPart,binding.loanAmountImage)
                disableSelection(binding.loanAmountEdittext, setSelection = false)
            }
            SELECT_PART.PERIOD -> {
                selection(binding.loanPeriod,binding.loanPeriodPart,binding.loanPeriodImage)
                disableSelection(binding.loanYearEdittext, binding.loanMonthEdittext, setSelection = false)
            }
            SELECT_PART.RATE -> {
                selection(binding.loanRate,binding.loanRatePart,binding.loanRateImage)
                disableSelection(binding.loanRateEdittext, setSelection = false)
            }
            SELECT_PART.PAYMENT -> {
                selection(binding.loanPayment,binding.loanPaymentPart,binding.loanPaymentImage)
                disableSelection(binding.loanPaymentEdittext, setSelection = false)
            }
        }
        viewmodel.setSelection = type
    }


    private fun selection(backgroundResource: View, backgroundColor: View, imageView: AppCompatImageView) {
        backgroundResource.setBackgroundResource(R.drawable.radius_10_blue)
        backgroundColor.setBackgroundColor(resources.getColor(R.color.light_blue_100))
        imageView.setImageResource(R.drawable.ic_lock)
    }

    private fun resetSelection() {
        setBackgroundResources(
            resource = R.drawable.radius_10_white,
            binding.loanPayment,binding.loanAmount,binding.loanRate,binding.loanPeriod)

        setBackgroundColor(
            color = resources.getColor(R.color.color_gray_four),
            binding.loanPaymentPart,binding.loanAmountPart,binding.loanRatePart,binding.loanPeriodPart
        )
        setImageResources(
            resource = R.drawable.ic_unlock,
            binding.loanAmountImage,binding.loanRateImage,binding.loanPaymentImage,binding.loanPeriodImage
        )

        disableSelection(
            binding.loanPaymentEdittext, binding.loanYearEdittext, binding.loanMonthEdittext, binding.loanRateEdittext, binding.loanAmountEdittext,
            setSelection = true
        )

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        defaultSelection()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        defaultSelection()

        //showPieChart()
        /*binding.filter.setOnClickListenerDebounce {
            filterLoanBottomSheet {
                onApplyClicked = { amount, rate, period ->
                }
            }?.show(childFragmentManager, FilterLoanBottomSheet::class.java.canonicalName)
        }*/

    }

    private fun defaultSelection() {
        resetValues()
        resetSelection()
        selection(binding.loanPayment,binding.loanPaymentPart,binding.loanPaymentImage)
        disableSelection(binding.loanPaymentEdittext, setSelection = false)
        showPieChart(
            totalInterest = 6618.55F,
            totalPayment = 106618.55F
        )
    }


    override fun observeState(state: LoanPageState) {
        when(state){

            else -> {}
        }
    }


}
