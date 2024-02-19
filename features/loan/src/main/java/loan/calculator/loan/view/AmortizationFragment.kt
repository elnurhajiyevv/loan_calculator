package loan.calculator.loan.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import loan.calculator.common.extensions.overrideColor
import loan.calculator.core.base.BaseFragment
import loan.calculator.loan.R
import loan.calculator.loan.adapter.AmortizationAdapter
import loan.calculator.loan.databinding.FragmentAmortizationBinding
import loan.calculator.loan.effect.AmortizationPageEffect
import loan.calculator.loan.state.AmortizationPageState
import loan.calculator.loan.viewmodel.AmortizationPageViewModel
import loan.calculator.uikit.extension.getImageResource

@AndroidEntryPoint
class AmortizationFragment : BaseFragment<AmortizationPageState, AmortizationPageEffect, AmortizationPageViewModel, FragmentAmortizationBinding>() {

    private val args by navArgs<AmortizationFragmentArgs>()
    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAmortizationBinding
        get() = FragmentAmortizationBinding::inflate

    private lateinit var amortizationAdapter: AmortizationAdapter

    override fun getViewModelClass() = AmortizationPageViewModel::class.java
    override fun getViewModelScope() = this

    override val bindViews: FragmentAmortizationBinding.() -> Unit = {
        toolbar.setBackButtonVisibility(show = true)
        amortizationAdapter = AmortizationAdapter()
        recyclerViewAmortization.adapter = amortizationAdapter
        toolbar.setGravityLeft()
        //include.logo.background.overrideColor(args?.loan?.type?.backgroundColor)
        include.titleText.text = args?.loanInfo?.name
        include.startDate.text = args?.loanInfo?.startDate
        include.paidOff.text = args?.loanInfo?.paidOff
        include.loan.text = "$ ${args?.loanInfo?.loanAmount}"
        include.interestRate.text = "${args?.loanInfo?.interestRate}%"
        include.frequency.text = args?.loanInfo?.frequency
        include.totalRepayment.text = "$ ${args?.loanInfo?.totalRepayment}"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        amortizationAdapter.submitList(args.loan.amortizationItems.toMutableList())
    }
}