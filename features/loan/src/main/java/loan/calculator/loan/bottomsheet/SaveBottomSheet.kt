package loan.calculator.loan.bottomsheet

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.graphics.drawable.Icon
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import loan.calculator.common.extensions.addCorners
import loan.calculator.common.extensions.dp
import loan.calculator.core.base.BaseNotSerializableBottomSheet
import loan.calculator.loan.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import loan.calculator.common.extensions.asFormattedDateWithDot
import loan.calculator.domain.entity.saved.GetSavedLoanModel
import loan.calculator.domain.entity.unit.IconModel
import loan.calculator.loan.adapter.IconAdapter
import loan.calculator.loan.databinding.DialogFragmentSaveBinding
import loan.calculator.loan.view.SaveDialog
import loan.calculator.uikit.util.calculatePaidOff
import java.util.Date
import kotlin.random.Random


class SaveBottomSheet : BaseNotSerializableBottomSheet() {

    private var onSaveButtonClicked: ((GetSavedLoanModel) -> Unit)? = null
    private var onDismiss: (() -> Unit)? = null
    private var onIconSelection: ((IconModel) -> Unit)? = null

    private var itemList: List<IconModel>? = null
    private var amount: String?=null
    private var period: String?=null
    private var rate: String?=null
    private var payment: String?=null
    private var frequency: String?=null
    private var termInMonth: String?=null
    private var totalInterest: String?=null
    private var totalPayment: String?=null
    private var iconModel: IconModel?=null

    var selectedType = ""
    var selectedStartDate = Date()

    lateinit var iconAdapter: IconAdapter


    override var showFullscreen: Boolean
        get() = true
        set(value) {}


    private lateinit var binding: DialogFragmentSaveBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentSaveBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
        }
        initView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initView() {
        with(binding) {
            mainLayout.addCorners(
                solidColor = R.color.background_color,
                radius = floatArrayOf(
                    24.dp.toFloat(),
                    24.dp.toFloat(),
                    24.dp.toFloat(),
                    24.dp.toFloat(),
                    0f,
                    0f,
                    0f,
                    0f
                )
            )

            iconText.text = iconModel?.iconResource?.type
            iconAdapter = IconAdapter(IconAdapter.IconItemClick{
                iconText.text = it.iconResource.type
                onIconSelection?.invoke(it)
                selectedType = it.iconResource.type
            })
            recyclerViewIcon.adapter = iconAdapter
            iconAdapter.submitList(itemList)

            cancelButton.setOnClickListener {
                this@SaveBottomSheet.dismiss()
            }
            saveButton.setOnClickListener {
                onSaveButtonClicked?.invoke(GetSavedLoanModel(
                    name = binding.nameEdittext.text.toString(),
                    code = Random(100).toString(),
                    description = "sometext",
                    type = selectedType,
                    background = "",
                    src = "",
                    startDate = binding.dataText.text.toString(),
                    paidOff = calculatePaidOff(period?.toInt() ?: 0, selectedStartDate),
                    loanAmount = amount,
                    interestRate = rate,
                    compoundingFrequency = frequency,
                    period = period,
                    totalRePayment = payment,
                    termInMonth = termInMonth?.toInt(),
                    totalInterest = totalInterest,
                    totalPayment = totalPayment
                ))
                this@SaveBottomSheet.dismiss()
            }
            nameEdittext.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun afterTextChanged(s: Editable) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if(s.trim().length>3){
                        saveButton.isEnabled = true
                        saveButton.setTextColor(resources.getColor(R.color.white))
                    } else {
                        saveButton.isEnabled = false
                        saveButton.setTextColor(resources.getColor(R.color.gray_text))
                    }

                }
            })
            calendar.setOnClickListener {
                datePickerBottomSheet {
                    onDateSelected {
                        updateSelectedDate(it)
                    }
                }.show(childFragmentManager, DatePickerBottomSheet::class.java.canonicalName)
            }
            updateSelectedDate(Date())
        }
    }

    private fun updateSelectedDate(it: Date) {
        selectedStartDate = it
        binding.dataText.text = it.asFormattedDateWithDot()
        binding.dataText.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.color_pure_black
            )
        )
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss?.invoke()
    }


    /**
     * get Selected Date from Date Picker
     * @return a java.util.Date
     */
    class Builder {
        private var itemList: List<IconModel>? = null
        private var amount: String? = null
        private var period: String? = null
        private var rate: String? = null
        private var payment: String? = null
        private var frequency: String? = null
        private var termInMonth: String? = null
        private var totalInterest: String? = null
        private var totalPayment: String? = null
        private var iconModel: IconModel? = null
        private var onSaveButtonClicked: ((GetSavedLoanModel) -> Unit)? = null
        private var onIconSelection: ((IconModel) -> Unit)? = null
        private var onDismiss: (() -> Unit)? = null

        fun setIconModel(iconModel: IconModel){
            this.iconModel = iconModel
        }

        fun itemList(itemList: () -> List<IconModel>?) {
            this.itemList = itemList()
        }

        fun setAmount(amount: String?){
            this.amount = amount
        }

        fun setPeriod(period: String){
            this.period = period
        }

        fun setRate(rate: String){
            this.rate = rate
        }

        fun setPayment(payment: String){
            this.payment = payment
        }

        fun setFrequency(frequency: String){
            this.frequency = frequency
        }

        fun setTermInMonth(termInMonth: String){
            this.termInMonth = termInMonth
        }

        fun setTotalInterest(totalInterest: String){
            this.totalInterest = totalInterest
        }

        fun setTotalPayment(totalPayment: String){
            this.totalPayment = totalPayment
        }

        fun onSaveButtonClicked(onSaveButtonClicked: (GetSavedLoanModel) -> Unit) {
            this.onSaveButtonClicked = onSaveButtonClicked
        }

        fun onIconSelection(onIconSelection: (IconModel) -> Unit) {
            this.onIconSelection = onIconSelection
        }

        fun onDismiss(onDismiss: () -> Unit) {
            this.onDismiss = onDismiss
        }

        fun build(): SaveBottomSheet {
            val bottomSheet = SaveBottomSheet()
            bottomSheet.iconModel = iconModel
            bottomSheet.itemList = itemList
            bottomSheet.amount = amount
            bottomSheet.period = period
            bottomSheet.rate = rate
            bottomSheet.payment = payment
            bottomSheet.frequency = frequency
            bottomSheet.termInMonth = termInMonth
            bottomSheet.totalInterest = totalInterest
            bottomSheet.totalPayment = totalPayment
            bottomSheet.onSaveButtonClicked = onSaveButtonClicked
            bottomSheet.onIconSelection = onIconSelection
            return bottomSheet
        }
    }


}

fun saveBottomSheet(lambda: SaveBottomSheet.Builder.() -> Unit) =
    SaveBottomSheet.Builder().apply(lambda).build()
