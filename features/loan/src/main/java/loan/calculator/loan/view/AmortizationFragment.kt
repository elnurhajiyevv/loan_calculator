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
import loan.calculator.core.extension.toast
import loan.calculator.domain.entity.home.AmortizationModel
import loan.calculator.domain.entity.unit.IconModel
import loan.calculator.loan.adapter.AmortizationAdapter
import loan.calculator.loan.bottomsheet.SaveBottomSheet
import loan.calculator.loan.bottomsheet.saveBottomSheet
import loan.calculator.loan.databinding.FragmentAmortizationBinding
import loan.calculator.loan.effect.AmortizationPageEffect
import loan.calculator.loan.state.AmortizationPageState
import loan.calculator.loan.viewmodel.AmortizationPageViewModel
import loan.calculator.uikit.util.returnValueIfNull
import java.util.Locale

@AndroidEntryPoint
class AmortizationFragment : BaseFragment<AmortizationPageState, AmortizationPageEffect,
        AmortizationPageViewModel, FragmentAmortizationBinding>() {

    private val args by navArgs<AmortizationFragmentArgs>()
    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAmortizationBinding
        get() = FragmentAmortizationBinding::inflate

    private lateinit var amortizationAdapter: AmortizationAdapter

    override fun getViewModelClass() = AmortizationPageViewModel::class.java
    override fun getViewModelScope() = this

    override val bindViews: FragmentAmortizationBinding.() -> Unit = {
        toolbar.setBackButtonVisibility(show = true)

        toolbar.setGravityLeft()
        /*include.logo.setImageResource(args.type.getImageResource())
        include.titleText.text = args.name*/

        viewmodel.saveLoanObject.apply {
            amount = args.loanAmount
            period = args.termInMonth.toInt().toString()
            rate = args.interestRate
            paymentAmount = args.totalRepayment
            frequencyRate = args.frequency
            totalPayment = args.totalRepayment
            termInMonth = args.termInMonth
            totalInterest = args.totalInterest
        }
        include.startDate.text = args.startDate
        include.paidOff.text = args.paidOff
        include.loan.text = "${args.currency} ${args.loanAmount}"
        include.interestRate.text = "${args.interestRate}%"
        include.frequency.text = args.frequency
        include.totalRepayment.text = "${args.currency} ${args.totalRepayment}"

        toolbar.setToolbarRightActionClick {
            viewmodel.getIconModelList()
        }
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

        var numOfItems = 0
        for(i in list.indices){
            var item = list[i]
            if(((item?.month)?.rem(12) ?: 0) == 0){
                numOfItems++
                item?.numberOfItems = numOfItems
                arrayList.add(item)
                numOfItems++
                arrayList.add(AmortizationModel(0,0.0,0.0,0.0,0.0,1,list[i]?.month?.div(12) ?: 0,numOfItems))
            } else {
                numOfItems++
                item?.numberOfItems = numOfItems
                arrayList.add(item)
            }
        }
        /*list.map {
            if((it?.month)?.rem(12) ?: 0 == 0){
                arrayList.add(it)
                arrayList.add(AmortizationModel((it?.month?:0),0.0,0.0,0.0,0.0,1,it?.month?.div(12) ?: 0))
            } else
                arrayList.add(it)
        }*/

        val locale = when(args.currency){
            "₼" -> Locale("az", "AZ")
            "₺" -> Locale("tr", "TR")
            "$" -> Locale.US
            "₽" -> Locale("ru", "RU")
            "€" -> Locale("es", "ES")
            else -> Locale.US
        }
        amortizationAdapter = AmortizationAdapter(
            locale = locale,
            context = requireContext(),
            arrayList,
            AmortizationAdapter.AmortizationModelClick{
            // handle on click listener
        })
        binding.recyclerViewAmortization.adapter = amortizationAdapter
    }

    override fun observeEffect(effect: AmortizationPageEffect) {
        when(effect){
            is AmortizationPageEffect.ListOfIconModel ->{
                saveValues(effect.list)
            }
            is AmortizationPageEffect.InsertSavedLoan -> {
                toast("Your loan added to favorite.")
            }
        }
    }

    private fun saveValues(list: List<IconModel>) {
        saveBottomSheet {
            itemList {
                list
            }
            setAmount(viewmodel.saveLoanObject.amount)
            setPeriod(viewmodel.saveLoanObject.period)
            setRate(viewmodel.saveLoanObject.rate)
            setPayment(viewmodel.saveLoanObject.paymentAmount)
            setFrequency(viewmodel.saveLoanObject.frequencyRate)
            setTermInMonth(viewmodel.saveLoanObject.termInMonth)
            setTotalInterest(viewmodel.saveLoanObject.totalInterest)
            setTotalPayment(viewmodel.saveLoanObject.totalPayment)
            setIconModel(viewmodel.getIconModel())
            setCurrency(currency = args.currency)
            onSaveButtonClicked {
                viewmodel.insertSavedLoan(
                    model = it
                )
            }
            onIconSelection {
                viewmodel.setIconModel(it)
            }
        }.show(childFragmentManager, SaveBottomSheet::class.java.canonicalName)
    }
}