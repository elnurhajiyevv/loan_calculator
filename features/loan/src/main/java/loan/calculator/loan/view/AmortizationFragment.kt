package loan.calculator.loan.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import loan.calculator.common.extensions.getDoubleValue
import loan.calculator.common.extensions.getIntValue
import loan.calculator.common.library.util.calculateAmortization
import loan.calculator.core.base.BaseFragment
import loan.calculator.domain.entity.home.AmortizationModel
import loan.calculator.loan.adapter.AmortizationAdapter
import loan.calculator.loan.databinding.FragmentAmortizationBinding
import loan.calculator.loan.effect.AmortizationPageEffect
import loan.calculator.loan.state.AmortizationPageState
import loan.calculator.loan.viewmodel.AmortizationPageViewModel
import loan.calculator.uikit.R
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

        toolbar.setGravityLeft()
        include.logo.setImageResource(args?.type?.getImageResource() ?: R.drawable.bg_balance)
        include.titleText.text = args?.name
        include.startDate.text = args?.startDate
        include.paidOff.text = args?.paidOff
        include.loan.text = "$ ${args?.loanAmount}"
        include.interestRate.text = "${args?.interestRate}%"
        include.frequency.text = args?.frequency
        include.totalRepayment.text = "$ ${args?.totalRepayment}"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var list = calculateAmortization(
            loanAmount = args.loanAmount.getDoubleValue(),
            termInMonths = args.termInMonth.getIntValue(),
            annualInterestRate = args.interestRate.getDoubleValue()
        )
        var arrayList = arrayListOf<AmortizationModel?>()
        arrayList.clear()
        list.map {
            if((it?.month)?.rem(12) ?: 0 == 0)
                arrayList.add(AmortizationModel((it?.month?:0),0.0,0.0,0.0,0.0,1,it?.month?.div(12) ?: 0))
            arrayList.add(it)
        }
        amortizationAdapter = AmortizationAdapter(arrayList,AmortizationAdapter.AmortizationModelClick{
            // handle on click listener
        })
        binding.recyclerViewAmortization.adapter = amortizationAdapter
    }
}