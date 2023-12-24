package loan.calculator.loan.bottomsheet

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import loan.calculator.common.library.util.DateFormats.convertDateToCalendar
import loan.calculator.core.base.BaseNotSerializableBottomSheet
import loan.calculator.loan.databinding.DataPickerBottomSheetBinding
import java.util.*

class DatePickerBottomSheet: BaseNotSerializableBottomSheet(), View.OnClickListener {
    lateinit var args: Args

    private lateinit var binding: DataPickerBottomSheetBinding

    override var showFullscreen: Boolean
        get() = false
        set(value) {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataPickerBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            state = BottomSheetBehavior.STATE_EXPANDED

            // prevent accidental closing of the bottom sheet when swiping DatePicker
            isDraggable = false
        }
        initView()
    }

    private fun initView() {
        with(binding) {
            makeRoundedCorners(mainLayout)
            backButton.setOnClickListener(this@DatePickerBottomSheet)
            selectButton.setOnClickListener(this@DatePickerBottomSheet)
            val minDate = Calendar.getInstance().apply {
                set(Calendar.HOUR, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.MILLISECOND, 0)
            }
            binding.datePicker.minDate = minDate.timeInMillis
        }
    }

    private fun updatePicker(date: Date) {
        val cal = date.convertDateToCalendar()
        binding.datePicker.updateDate(
            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
        )
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.backButton -> this.dismiss()
            binding.selectButton -> {
                try {
                    args.onDateSelected?.invoke(getSelectedDateFromPicker())
                    this.dismiss()
                } catch (e: NoSuchElementException) {
                    //ignored, looger will be available
                }
            }
        }
    }

    /**
     * get Selected Date from Date Picker
     * @return a java.util.Date
     */
    private fun getSelectedDateFromPicker(): Date {
        return with(binding.datePicker) {
            val day = dayOfMonth
            val month = month
            val year = year
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            calendar.time
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        args.onDismiss?.invoke()
    }

    class Args {
        var onDateSelected: ((Date) -> Unit)? = null
        var onDismiss: (() -> Unit)? = null

        fun onDateSelected(onDateSelected: (Date) -> Unit) {
            this.onDateSelected = onDateSelected
        }

        fun onDismiss(onDismiss: () -> Unit) {
            this.onDismiss = onDismiss
        }
    }
}

fun datePickerBottomSheet(lambda: DatePickerBottomSheet.Args.() -> Unit): DatePickerBottomSheet {
    val bottomSheet = DatePickerBottomSheet()
    bottomSheet.args = DatePickerBottomSheet.Args().apply(lambda)
    return bottomSheet
}
