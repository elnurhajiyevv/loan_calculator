package loan.calculator.loan.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import loan.calculator.core.base.BaseFragment
import loan.calculator.domain.entity.home.Loan
import loan.calculator.loan.adapter.AmortizationAdapter
import loan.calculator.loan.databinding.FragmentAmortizationBinding
import loan.calculator.loan.effect.AmortizationPageEffect
import loan.calculator.loan.state.AmortizationPageState
import loan.calculator.loan.viewmodel.AmortizationPageViewModel

@AndroidEntryPoint
class AmortizationFragment : BaseFragment<AmortizationPageState, AmortizationPageEffect, AmortizationPageViewModel, FragmentAmortizationBinding>() {

    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAmortizationBinding
        get() = FragmentAmortizationBinding::inflate

    lateinit var amortizationAdapter: AmortizationAdapter

    override fun getViewModelClass() = AmortizationPageViewModel::class.java
    override fun getViewModelScope() = this

    override val bindViews: FragmentAmortizationBinding.() -> Unit = {
        toolbar.setBackButtonVisibility(show = true)
        amortizationAdapter = AmortizationAdapter()
        recyclerViewAmortization.adapter = amortizationAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var loan = Loan(
            loanAmount = 10000.0,
            termInYears = 2,
            annualInterestRate = 17.0,
            downPayment = 14.0,
            tradeInValue = 20.0,
            salesTaxRate = 4.0,
            fees = 4.0)
        amortizationAdapter.submitList(loan.amortizationItems.toMutableList())
    }
}