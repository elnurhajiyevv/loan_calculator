/*
 * Created by Elnur Hajiyev on on 8/6/22, 2:32 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.exchange.loan.view

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import loan.exchange.common.extensions.setOnClickListenerDebounce
import loan.exchange.core.base.BaseFragment
import loan.exchange.domain.entity.home.SeekbarModel
import loan.exchange.loan.R
import loan.exchange.loan.bottomsheet.FilterLoanBottomSheet
import loan.exchange.loan.bottomsheet.ResultLoanBottomSheet
import loan.exchange.loan.bottomsheet.filterLoanBottomSheet
import loan.exchange.loan.bottomsheet.resultLoanBottomSheet
import loan.exchange.loan.databinding.FragmentLoanPageBinding
import loan.exchange.loan.effect.LoanPageEffect
import loan.exchange.loan.state.LoanPageState
import loan.exchange.loan.viewmodel.LoanPageViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dagger.hilt.android.AndroidEntryPoint
import loan.exchange.common.extensions.gone
import loan.exchange.common.extensions.show
import loan.exchange.domain.entity.home.CurrencyModel
import loan.exchange.uikit.bottomsheet.CurrencyMenuBottomSheet
import loan.exchange.uikit.bottomsheet.currencyMenuBottomSheet
import loan.exchange.uikit.extension.enableSumFormatting

@AndroidEntryPoint
class LoanPageFragment : BaseFragment<LoanPageState, LoanPageEffect, LoanPageViewModel, FragmentLoanPageBinding>() {


    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoanPageBinding
        get() = FragmentLoanPageBinding::inflate

    override fun getViewModelClass() = LoanPageViewModel::class.java
    override fun getViewModelScope() = this

    var isFromEdittext = false
    var isCalculationWithSeekbar = true

    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //startAppAd = StartAppAd(this)
    }

    private fun setValueAmount(value: SeekbarModel){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.loanAmount.min = value.min
        }
        binding.loanAmount.max = value.max
        binding.loanAmount.progress = value.min
        binding.currency.text = "${value.min}"
        binding.loanSourceLeft.text = getString(R.string.loan_amount_left,value.min.toString())
        binding.loanSourceRight.text = getString(R.string.loan_amount_right,value.max.toString())
    }

    private fun setValueRate(value: SeekbarModel){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.loanRate.min = value.min
        }
        binding.loanRate.max = value.max
        binding.loanRate.progress = value.min
        binding.rate.text = "${value.min}"
        binding.loanRateLeft.text = getString(R.string.loan_rate_left,value.min.toString())
        binding.loanRateRight.text = getString(R.string.loan_rate_right,value.max.toString())
    }

    private fun setValuePeriod(value: SeekbarModel){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.loanPeriod.min = value.min
        }
        binding.loanPeriod.max = value.max
        binding.loanPeriod.progress = value.min
        binding.period.text = "${value.min}"
        binding.loanPeriodLeft.text = getString(R.string.loan_period_left,value.min.toString())
        binding.loanPeriodRight.text = getString(R.string.loan_period_right,value.max.toString())
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

        binding.loanAmount.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
                try {
                    binding.currency.text = "$progress$"
                    var total = viewmodel.calculateTotal(
                        amount = progress.toDouble(),
                        rate = binding.loanRate.progress.toDouble(),
                        period = binding.loanPeriod.progress.toDouble())
                    binding.bottomAmount.text = "${String.format("%.2f", viewmodel.calculateMonthly(
                        total,
                        period = binding.loanPeriod.progress.toDouble()))}$"
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {}
        })

        binding.loanAmountText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do Nothing
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                try {
                    var vator = if(s.isNullOrEmpty()) 1.0 else s.toString().toDouble()

                    var total = viewmodel.calculateTotal(
                        amount = vator,
                        rate = binding.loanRateText.toString().toDouble(),
                        period = binding.loanPeriodText.toString().toDouble())
                    binding.bottomAmount.text = "${String.format("%.2f", viewmodel.calculateMonthly(
                        total,
                        period = binding.loanPeriodText.toString().toDouble()))}$"
                } catch (e:Exception){
                    e.printStackTrace()
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Do Nothing
            }

        })


        binding.loanRate.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
                try {
                    binding.rate.text = "$progress %"
                    var total = viewmodel.calculateTotal(
                        amount = binding.loanAmount.progress.toDouble(),
                        rate = progress.toDouble(),
                        period = binding.loanPeriod.progress.toDouble()
                    )
                    binding.bottomAmount.text = "${
                        String.format(
                            "%.2f",
                            viewmodel.calculateMonthly(
                                total = total,
                                period = binding.loanPeriod.progress.toDouble()
                            )
                        )
                    }$"
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {}
        })

        binding.loanRateText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do Nothing
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                try {
                    var vator = if(s.isNullOrEmpty()) 1.0 else s.toString().toDouble()

                    var total = viewmodel.calculateTotal(
                        amount = binding.loanAmountText.text.toString().toDouble(),
                        rate = vator,
                        period = binding.loanPeriodText.text.toString().toDouble()
                    )
                    binding.bottomAmount.text = "${
                        String.format(
                            "%.2f",
                            viewmodel.calculateMonthly(
                                total = total,
                                period = binding.loanPeriodText.text.toString().toDouble()
                            )
                        )
                    }$"
                } catch (e:Exception){
                    e.printStackTrace()
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Do Nothing
            }

        })


        binding.loanPeriod.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
                try {
                    binding.period.text = "$progress"
                    var total = viewmodel.calculateTotal(
                        amount = binding.loanAmount.progress.toDouble(),
                        rate = binding.loanRate.progress.toDouble(),
                        period = progress.toDouble()
                    )
                    binding.bottomAmount.text = "${
                        String.format(
                            "%.2f",
                            viewmodel.calculateMonthly(
                                total = total,
                                period = progress.toDouble()
                            )
                        )
                    }$"
                } catch (e:Exception){
                    e.printStackTrace()
                }
            }
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {}
        })

        binding.loanPeriodText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do Nothing
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                try {
                    var vator = if(s.isNullOrEmpty()) 1.0 else s.toString().toDouble()

                    var total = viewmodel.calculateTotal(
                        amount = binding.loanAmountText.text.toString().toDouble(),
                        rate = binding.loanRateText.text.toString().toDouble(),
                        period = vator
                    )
                    binding.bottomAmount.text = "${
                        String.format(
                            "%.2f",
                            viewmodel.calculateMonthly(
                                total = total,
                                period = vator
                            )
                        )
                    }$"
                } catch (e:Exception){
                    e.printStackTrace()
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Do Nothing
            }

        })


        binding.filter.setOnClickListenerDebounce {
            filterLoanBottomSheet {
                onApplyClicked = { amount, rate, period ->
                    setValueAmount(amount)
                    setValueRate(rate)
                    setValuePeriod(period)
                }
            }?.show(childFragmentManager, FilterLoanBottomSheet::class.java.canonicalName)
        }

        binding.filterCurrency.setOnClickListenerDebounce {
            viewmodel.getCurrency()
        }

        binding.apply.setOnClickListenerDebounce {
            mInterstitialAd?.show(requireActivity()) ?: Log.d("TAG", "The interstitial ad wasn't ready yet.")
            resultLoanBottomSheet {
                var loanAmount = 0.0
                var loanRate = 0.0
                var loanPeriod = 0.0

                if(isFromEdittext){
                    try {
                        loanAmount = binding.loanAmountText.text.toString().toDouble()
                        loanRate = binding.loanRateText.text.toString().toDouble()
                        loanPeriod = binding.loanPeriodText.text.toString().toDouble()
                    } catch (e:Exception){
                        e.printStackTrace()
                    }
                } else {
                    loanAmount = binding.loanAmount.progress.toDouble()
                    loanRate = binding.loanRate.progress.toDouble()
                    loanPeriod = binding.loanPeriod.progress.toDouble()
                }

                var total = viewmodel.calculateTotal(
                    amount = loanAmount,
                    rate = loanRate,
                    period = loanPeriod)

                var monthly = viewmodel.calculateMonthly(total = total,loanPeriod)

                setMonthly(monthly = "${String.format("%.2f", monthly)}")
                setMonth(month = loanPeriod.toString())
                setTotal(total)
                setInterestRate(interestRate = loanRate.toString())
                setInterest(viewmodel.calculateInterest(total = total, amount = loanAmount))
            }?.show(childFragmentManager, ResultLoanBottomSheet::class.java.canonicalName)
        }

    }

    private fun updateSelectedAmount(it: CurrencyModel) {
        viewmodel.selectedCurrency = it.name
    }

    override fun observeState(state: LoanPageState) {
        when(state){
            is LoanPageState.GetCurrency -> {
                currencyMenuBottomSheet {
                    itemList = state.response
                    onItemsSelected = {
                        updateSelectedAmount(it)
                    }
                    onDismiss = {

                    }
                }?.show(childFragmentManager, CurrencyMenuBottomSheet::class.java.canonicalName)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.loanAmount.progress = 1
        binding.loanPeriod.progress = 1
        binding.loanRate.progress = 1
        viewmodel.selectedCurrency = ""
        viewmodel.availableCurrency.clear()
    }
}
