package loan.calculator.save.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import loan.calculator.common.library.util.calculateAmortization
import loan.calculator.core.base.BaseFragment
import loan.calculator.save.adapter.SavedAmortizationAdapter
import loan.calculator.save.effect.AmortizationPageEffect
import loan.calculator.save.state.AmortizationPageState
import loan.calculator.save.viewmodel.SavedAmortizationPageViewModel
import loan.calculator.uikit.databinding.FragmentAmortizationBinding
import loan.calculator.uikit.extension.getImageResource

@AndroidEntryPoint
class SavedAmortizationFragment : BaseFragment<AmortizationPageState, AmortizationPageEffect, SavedAmortizationPageViewModel, FragmentAmortizationBinding>() {

    private val args by navArgs<SavedAmortizationFragmentArgs>()
    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAmortizationBinding
        get() = FragmentAmortizationBinding::inflate

    private lateinit var amortizationAdapter: SavedAmortizationAdapter

    override fun getViewModelClass() = SavedAmortizationPageViewModel::class.java
    override fun getViewModelScope() = this

    override val bindViews: FragmentAmortizationBinding.() -> Unit = {
        toolbar.setBackButtonVisibility(show = true)
        amortizationAdapter = SavedAmortizationAdapter()
        recyclerViewAmortization.adapter = amortizationAdapter
        toolbar.setGravityLeft()
        args?.loanInfo?.type?.getImageResource()?.let { include.logo.setImageResource(it) }
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
        var list = calculateAmortization(
            loanAmount = args.loanInfo.loanAmount,
            termInMonths = args.loanInfo.termInMonth?:0,
            annualInterestRate = args.loanInfo.interestRate
        )
        amortizationAdapter.submitList(list.toMutableList())
    }
}