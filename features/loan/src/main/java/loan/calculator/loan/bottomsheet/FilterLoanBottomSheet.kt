package loan.calculator.loan.bottomsheet

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import loan.calculator.common.extensions.addCorners
import loan.calculator.common.extensions.dp
import loan.calculator.core.base.BaseNotSerializableBottomSheet
import loan.calculator.domain.entity.home.SeekbarModel
import loan.calculator.domain.entity.home.SeekbarName
import loan.calculator.loan.R
import loan.calculator.loan.databinding.DialogFragmentFilterBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class FilterLoanBottomSheet : BaseNotSerializableBottomSheet() {

    private var backButton = false
    private var onDismiss: (() -> Unit)? = null
    private var onApplyClicked: ((amount: SeekbarModel, rate: SeekbarModel, period: SeekbarModel) -> Unit)? = null
    lateinit var binding: DialogFragmentFilterBinding

    override var showFullscreen: Boolean
        get() = false
        set(value) {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogFragmentFilterBinding.inflate(inflater, container, false)
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
                solidColor = R.color.color_pure_white,
                radius = floatArrayOf(24.dp.toFloat(), 24.dp.toFloat(), 24.dp.toFloat(), 24.dp.toFloat(), 0f, 0f, 0f, 0f)
            )

            binding.applyButton.setOnClickListener {
                dismiss()

                var loanAmount = SeekbarModel(name = SeekbarName.LOAN_AMOUNT,
                    min = loanAmountSeekbar.selectedMinValue.toInt(),
                    max = loanAmountSeekbar.selectedMaxValue.toInt())

                var loanRate = SeekbarModel(name = SeekbarName.LOAN_RATE,
                    min = loanRateSeekbar.selectedMinValue.toInt(),
                    max = loanRateSeekbar.selectedMaxValue.toInt())

                var loanPeriod = SeekbarModel(name = SeekbarName.LOAN_PERIOD,
                    min = loanPeriodSeekbar.selectedMinValue.toInt(),
                    max = loanPeriodSeekbar.selectedMaxValue.toInt())


                onApplyClicked?.invoke(loanAmount,loanRate, loanPeriod)
            }

        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss?.invoke()
    }


    class Builder {

        var onApplyClicked: ((amount: SeekbarModel, rate: SeekbarModel, period: SeekbarModel) -> Unit)? = null
        var onDismiss: (() -> Unit)? = null
        var onBack: (() -> Unit)? = null


        fun onApplyClicked(onApplyClicked: (amount: SeekbarModel, rate: SeekbarModel, period: SeekbarModel) -> Unit) {
            this.onApplyClicked = onApplyClicked
        }

        fun onDismiss(onDismiss: () -> Unit) {
            this.onDismiss = onDismiss
        }

        fun build(): FilterLoanBottomSheet? {
            val bottomSheet = FilterLoanBottomSheet()
            bottomSheet.onApplyClicked = onApplyClicked
            bottomSheet.onDismiss = onDismiss
            return bottomSheet
        }
    }


}

fun filterLoanBottomSheet(lambda: FilterLoanBottomSheet.Builder.() -> Unit) =
    FilterLoanBottomSheet.Builder().apply(lambda).build()