/*
 * Created by Elnur Hajiyev on on 8/6/22, 2:32 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.exchange.home.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import loan.exchange.common.extensions.setOnClickListenerDebounce
import loan.exchange.core.base.BaseFragment
import loan.exchange.domain.entity.home.CurrencyModel
import loan.exchange.home.R
import loan.exchange.home.databinding.FragmentHomePageBinding
import loan.exchange.home.effect.HomePageEffect
import loan.exchange.home.state.HomePageState
import loan.exchange.home.viewmodel.HomePageViewModel
import loan.exchange.uikit.extension.enableSumFormatting
import loan.exchange.uikit.extension.getImageResource
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import dagger.hilt.android.AndroidEntryPoint
import loan.exchange.domain.entity.home.response.LatestRatesResponseModel
import loan.exchange.home.adapter.CurrencyConvertedAdapter
import loan.exchange.uikit.bottomsheet.CurrencyMenuBottomSheet
import loan.exchange.uikit.bottomsheet.currencyMenuBottomSheet

@AndroidEntryPoint
class HomePageFragment : BaseFragment<HomePageState, HomePageEffect, HomePageViewModel, FragmentHomePageBinding>() {

    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomePageBinding
        get() = FragmentHomePageBinding::inflate

    override fun getViewModelClass() = HomePageViewModel::class.java
    override fun getViewModelScope() = this

    lateinit var currencyConvertedAdapter: CurrencyConvertedAdapter

    var amountFocus = false
    var convertedAmountFocus = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //startAppAd = StartAppAd(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialSelectedCurrency()

        currencyConvertedAdapter = CurrencyConvertedAdapter()

        withBinding {
            startAppBanner.loadAd()

            middle.setOnClickListener {
                replaceAmountAndConvertedAmount()
            }

            recyclerViewConvertedCurrency.adapter = currencyConvertedAdapter

            chart1.setNoDataText("")
            amountEdittext.enableSumFormatting()
            convertedAmountEdittext.enableSumFormatting()

            amountContainer.setOnClickListenerDebounce{
                convertedAmountContainer.isEnabled = false
                openCurrencyModule(SELECTION_TYPE.AMOUNT)
            }
            convertedAmountContainer.setOnClickListenerDebounce{
                amountContainer.isEnabled = false
                openCurrencyModule(SELECTION_TYPE.CONVERTED_AMOUNT)
            }


            amountEdittext.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun afterTextChanged(s: Editable) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (amountFocus) {
                        try {
                            val valor: Double = if (s.isNotEmpty()) s.toString().
                            replace(""," ").
                            replace(" ","").toDouble() else 0.0
                            convertedAmountEdittext.setText((valor * viewmodel.getSelectedRate()).toString())
                        }catch (e: Exception){
                            e.printStackTrace()
                        }
                    }
                }
            })

            convertedAmountEdittext.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun afterTextChanged(s: Editable) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (convertedAmountFocus) {
                        try {
                            val valor: Double = if (s.isNotEmpty()) s.toString().
                            replace(""," ").
                            replace(" ","").toDouble() else 0.0
                            amountEdittext.setText((valor * (1 / viewmodel.getSelectedRate())).toString())
                        }catch (e: Exception){
                            e.printStackTrace()
                        }
                    }
                }
            })

            amountEdittext.onFocusChangeListener =
                OnFocusChangeListener { view, b -> amountFocus = b }

            convertedAmountEdittext.onFocusChangeListener =
                OnFocusChangeListener { view, b -> convertedAmountFocus = b }
        }

    }

    private fun initialSelectedCurrency() {

        var selectedAmount = viewmodel.getSelectedAmount()
        var selectedConvert = viewmodel.getSelectedConverted()
        var selectedRate = viewmodel.getSelectedRate()

        updateSelectAmount(selectedAmount)
        updateSelectConvert(selectedConvert)
        updateRate(selectedAmount,selectedRate,selectedConvert)

    }

    private fun updateRate(selectedAmount:String, selectedRate: Float, selectedConvert: String){
        binding.exchangeRate.text = getString(R.string.converter_text,selectedAmount, selectedRate.toString(),selectedConvert)
    }

    private fun updateSelectAmount(selectedAmount:String){
        binding.amountImage.setImageResource(selectedAmount.getImageResource())
        binding.amountText.text = selectedAmount
    }

    private fun updateSelectConvert(selectedConvert:String){
        binding.convertedAmountImage.setImageResource(selectedConvert.getImageResource())
        binding.convertedAmountText.text = selectedConvert
    }

    private fun replaceAmountAndConvertedAmount() {
        binding.middle.animate().rotation(binding.middle.rotation + 180f).duration = 500
        try {
            var previousSelectedAmount = viewmodel.getSelectedAmount()
            var previousConvertedAmount = viewmodel.getSelectedConverted()
            var convertedRate = 1 / viewmodel.getSelectedRate()

            var temp = previousSelectedAmount
            previousSelectedAmount = previousConvertedAmount
            previousConvertedAmount = temp

            viewmodel.setLastSelectedAmount(previousSelectedAmount)
            viewmodel.setLastSelectedConverted(previousConvertedAmount)
            viewmodel.setLastSelectedRate(convertedRate)

            updateSelectAmount(previousSelectedAmount)
            updateSelectConvert(previousConvertedAmount)
            updateRate(previousSelectedAmount, convertedRate, previousConvertedAmount)

            viewmodel.latestExchangeRates(SELECTION_TYPE.AMOUNT, previousSelectedAmount)

            binding.amountEdittext.text = binding.convertedAmountEdittext.text

        } catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun observeState(state: HomePageState) {
        when(state){
            is HomePageState.GetLatestExchangeRates -> calculateRate(state.type,state.latestRatesResponseModel)
            is HomePageState.GetLatestExchangeRatesErrorResult -> {

            }
        }
    }

    private fun calculateRate(type: SELECTION_TYPE,latestRatesResponseModel: LatestRatesResponseModel) {

        var selected = viewmodel.getSelectedAmount()

        var converted = viewmodel.getSelectedConverted()

        var rate = 1.0f



        latestRatesResponseModel.result?.forEach { finded ->
            try {
                if(finded.key == if(type == SELECTION_TYPE.AMOUNT) converted else selected)
                    rate = finded.value.toFloat()
            } catch (e:Exception){
                e.printStackTrace()
            }
        }
        var convertedRate = if(type == SELECTION_TYPE.AMOUNT) rate else 1/rate

        currencyConvertedAdapter.submitList(viewmodel.availableCurrencyWithRate)

        viewmodel.setLastSelectedRate(rate = convertedRate)
        updateRate(
            selectedAmount = selected,
            selectedRate = convertedRate,
            selectedConvert = converted)
    }

    private fun openCurrencyModule(type: SELECTION_TYPE){
        currencyMenuBottomSheet {
            itemList = viewmodel.availableCurrency
            onItemsSelected = {
                updateSelectedAmount(type,it)
            }
            onDismiss = {
                binding.convertedAmountContainer.isEnabled = true
                binding.amountContainer.isEnabled = true
            }
        }?.show(childFragmentManager, CurrencyMenuBottomSheet::class.java.canonicalName)
    }

    override fun onDestroy() {
        super.onDestroy()
        //binding.exchangeRate.text = ""
    }

    private fun updateSelectedAmount(type: SELECTION_TYPE, model: CurrencyModel){
        var currency = model.name
        when (type) {
            SELECTION_TYPE.AMOUNT -> {

                selectAmount(currency)
                updateSelectAmount(currency)

                viewmodel.latestExchangeRates(type,currency)

                updateRate(
                    selectedAmount = model.name,
                    selectedRate = viewmodel.getSelectedRate(),
                    selectedConvert = viewmodel.getSelectedConverted())

                setLineChart()
                //validateLatestExchangeRate()

            }
            SELECTION_TYPE.CONVERTED_AMOUNT -> {

                selectConvert(currency)
                updateSelectConvert(currency)

                viewmodel.latestExchangeRates(type,currency)

                updateRate(viewmodel.getSelectedAmount(),viewmodel.getSelectedRate(),currency)
                //validateLatestExchangeRate()
            }
        }
    }

    private fun selectConvert(name: String) {
        viewmodel.setLastSelectedConverted(name)
    }

    private fun selectAmount(name: String) {
        viewmodel.setLastSelectedAmount(name)
    }

    fun setLineChart(){
        binding.chart1.setTouchEnabled(false)
        binding.chart1.isDragEnabled = true
        binding.chart1.setScaleEnabled(true)
        binding.chart1.setPinchZoom(false)
        binding.chart1.setDrawGridBackground(false)

        binding.chart1.description.isEnabled = false
        binding.chart1.axisLeft.isEnabled = false
        binding.chart1.axisRight.isEnabled = false
        binding.chart1.xAxis.isEnabled = false

        binding.chart1.animateXY(2000,2000)


        binding.chart1.maxHighlightDistance = 50f
        binding.chart1.setViewPortOffsets(0f, 0f, 0f, 0f)
        lineChartDownFillWithData()
    }

    private fun lineChartDownFillWithData() {
        val entryArrayList = ArrayList<Entry>()
        entryArrayList.add(Entry(0f, 5f, "1"))
        entryArrayList.add(Entry(1f, 6f, "2"))
        entryArrayList.add(Entry(2f, 5f, "3"))
        entryArrayList.add(Entry(3f, 7f, "4"))
        entryArrayList.add(Entry(4f, 4f, "5"))
        entryArrayList.add(Entry(5f, 6f, "6"))
        entryArrayList.add(Entry(6f, 5f, "7"))
        entryArrayList.add(Entry(7f, 6f, "8"))

        //LineDataSet is the line on the graph
        //LineDataSet is the line on the graph
        val lineDataSet = LineDataSet(entryArrayList,"")

        lineDataSet.lineWidth = 3f
        lineDataSet.color = getColor(R.color.dark_yellow)
        lineDataSet.setDrawValues(false)
        lineDataSet.circleRadius = 10f


        //to make the smooth line as the graph is adrapt change so smooth curve
        //to make the smooth line as the graph is adrapt change so smooth curve
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        //to enable the cubic density : if 1 then it will be sharp curve
        //to enable the cubic density : if 1 then it will be sharp curve
        lineDataSet.cubicIntensity = 0.2f

        //to fill the below of smooth line in graph

        //to fill the below of smooth line in graph
        lineDataSet.setDrawFilled(true)
        lineDataSet.fillColor = getColor(R.color.solid_blue)
        //set the transparency
        //set the transparency
        lineDataSet.fillAlpha = 80

        //set the gradiant then the above draw fill color will be replace

        //set the gradiant then the above draw fill color will be replace
        val drawable = ContextCompat.getDrawable(
            requireContext(), R.drawable.gradient
        )
        lineDataSet.setDrawFilled(true)
        lineDataSet.fillDrawable = drawable

        //set legend disable or enable to hide {the left down corner name of graph}
        //set legend disable or enable to hide {the left down corner name of graph}
        val legend: Legend = binding.chart1.legend
        legend.isEnabled = false

        //to remove the cicle from the graph

        //to remove the cricle from the graph
        lineDataSet.setDrawCircles(false)

        //lineDataSet.setColor(ColorTemplate.COLORFUL_COLORS);


        //lineDataSet.setColor(ColorTemplate.COLORFUL_COLORS);
        val iLineDataSetArrayList = ArrayList<ILineDataSet>()
        iLineDataSetArrayList.add(lineDataSet)

        //LineData is the data accord

        //LineData is the data accord
        val lineData = LineData(iLineDataSetArrayList)
        lineData.setValueTextSize(6f)
        lineData.setValueTextColor(getColor(R.color.solid_blue))
        binding.chart1.data = lineData
        binding.chart1.invalidate()
    }


    enum class SELECTION_TYPE(val type: String){
        AMOUNT("amount"),CONVERTED_AMOUNT("converted_amount")
    }
}
