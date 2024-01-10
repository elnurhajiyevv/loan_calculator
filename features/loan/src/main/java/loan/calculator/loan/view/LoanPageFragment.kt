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
import com.github.mikephil.charting.components.Description
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
import loan.calculator.common.extensions.getIntValue
import loan.calculator.common.extensions.setOnClickListenerDebounce
import loan.calculator.core.base.BaseFragment
import loan.calculator.core.tools.NavigationCommand
import loan.calculator.domain.entity.home.Loan
import loan.calculator.domain.entity.home.LoanInfo
import loan.calculator.loan.R
import loan.calculator.loan.databinding.FragmentLoanPageBinding
import loan.calculator.loan.effect.LoanPageEffect
import loan.calculator.loan.state.LoanPageState
import loan.calculator.loan.viewmodel.LoanPageViewModel
import loan.calculator.uikit.edittext.InputFilterMinMax
import loan.calculator.uikit.extension.enableSumFormatting
import loan.calculator.uikit.util.calculatePaidOff
import loan.calculator.uikit.util.disableSelection
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
            viewmodel.navigate(
                NavigationCommand.To(
                    LoanPageFragmentDirections.actionLoanPageFragmentToAmortizationFragment(
                        loan = Loan(
                            loanAmount = returnValueIfNull(binding.loanAmountEdittext).getDoubleValue(),
                            termInYears = returnValueIfNull(binding.loanYearEdittext).getIntValue(),
                            annualInterestRate = returnValueIfNull(binding.loanRateEdittext).getDoubleValue(),
                            downPayment = 0.0,
                            tradeInValue = 0.0,
                            salesTaxRate = 0.0,
                            fees = 0.0
                        ),
                        loanInfo = LoanInfo(
                            name = "",
                            backgroundColor = 0,
                            startDate = Date().asFormattedDateWithDot(),
                            paidOff = calculatePaidOff(
                                viewmodel.getPeriodInMonth(
                                    returnValueIfNull(binding.loanYearEdittext).toInt(),
                                    returnValueIfNull(binding.loanMonthEdittext).toInt()
                                ), Date()
                            ),
                            loanAmount = returnValueIfNull(binding.loanAmountEdittext),
                            interestRate = returnValueIfNull(binding.loanRateEdittext),
                            frequency = type.selectedItem.toString(),
                            totalRepayment = returnValueIfNull(binding.loanPaymentEdittext)
                        )
                    )
                )
            )
        }

        loanMonthEdittext.filters = arrayOf<InputFilter>(InputFilterMinMax(1, 12))
        loanYearEdittext.filters = arrayOf<InputFilter>(InputFilterMinMax(1, 99))

        loanAmountEdittext.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                try {
                    binding.loanPaymentEdittext.setText(viewmodel.calculateMonthlyPayment(
                        amount = getValor(s),
                        termInMonth = returnValueIfNull(loanMonthEdittext).toInt(),
                        termInYear = returnValueIfNull(loanYearEdittext).toInt(),
                        interestRate = getValor(returnValueIfNull(loanRateEdittext))
                    ).toString())
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }
        })

        loanRateEdittext.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                try {
                    // getValor(s)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        })

        loanPaymentEdittext.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                try {
                    //getValor(s)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        })

        loanMonthEdittext.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                try {
                    //getValor(s)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        })

        loanYearEdittext.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                try {
                    //getValor(s)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        })
    }

    fun getValor(s: CharSequence): Double {
        return if (s.isNotEmpty()) s.toString().replace("", " ").replace(" ", "")
            .toDouble() else 0.0
    }

    fun getFloatValue(s: String): Float {
        return if (s.isNotEmpty()) s.replace(" ", "").replace("$", "")
            .toFloat() else 0.0F
    }


    private fun saveValues() {
        val saveDialog = SaveDialog
        saveDialog.build(
            amount = returnValueIfNull(binding.loanAmountEdittext),
            period = returnValueIfNull(binding.loanYearEdittext),
            rate = returnValueIfNull(binding.loanRateEdittext),
            payment = returnValueIfNull(binding.loanPaymentEdittext),
            frequency = binding.type.selectedItem.toString()
        ).show(parentFragmentManager,"saveDialog")
    }

    private fun returnValueIfNull(editText: EditText): String{
        return if(editText.text.toString().trim().isNullOrEmpty())
            editText.hint.toString()
        else
            editText.text.toString()
    }

    private fun showPieChart(totalInterest: Float, totalPayment: Float){
        binding.chart.setUsePercentValues(true)
        binding.chart.setExtraOffsets(25f, 5f, 25f, 0f)
        binding.chart.isDrawHoleEnabled = true
        binding.chart.setHoleColor(Color.WHITE)

        val yvalues: MutableList<PieEntry> = ArrayList()
        yvalues.add(PieEntry(totalInterest, info[0]))
        yvalues.add(PieEntry(totalPayment, info[1]))
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
            0xFF00B2FF.toInt()
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
        binding.totalInterestValue.text = "$6 618.55"
        binding.totalRepaymentValue.text = "$106 618.55"
    }


    override fun observeState(state: LoanPageState) {
        when(state){

            else -> {}
        }
    }

    enum class SELECT_PART(type: String){
        AMOUNT("amount"),
        PERIOD("period"),
        RATE("rate"),
        PAYMENT("payment")
    }

    enum class SELECT_FREQUENCY(type: String){
        MONTHLY("Monthly"),
        WEEKLY("Weekly"),
        DAILY("Daily"),
        YEARLY("Yearly")
    }
}
