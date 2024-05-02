package loan.calculator.loan.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.palette.graphics.Palette
import dagger.hilt.android.AndroidEntryPoint
import loan.calculator.common.extensions.asFormattedDateWithDot
import loan.calculator.common.extensions.gone
import loan.calculator.common.extensions.setOnClickListenerDebounce
import loan.calculator.common.extensions.show
import loan.calculator.core.base.BaseDialogFragment
import loan.calculator.core.extension.DeeplinkNavigationTypes
import loan.calculator.core.extension.toast
import loan.calculator.core.tools.NavigationCommand
import loan.calculator.domain.entity.saved.GetSavedLoanModel
import loan.calculator.loan.R
import loan.calculator.loan.adapter.IconAdapter
import loan.calculator.loan.bottomsheet.DatePickerBottomSheet
import loan.calculator.loan.bottomsheet.datePickerBottomSheet
import loan.calculator.loan.databinding.DialogSaveBinding
import loan.calculator.loan.effect.SaveDialogEffect
import loan.calculator.loan.state.SaveDialogState
import loan.calculator.loan.viewmodel.SaveDialogViewModel

import loan.calculator.uikit.util.calculatePaidOff
import java.util.Date
import kotlin.random.Random

@AndroidEntryPoint
class SaveDialog: BaseDialogFragment<SaveDialogState,SaveDialogEffect,SaveDialogViewModel,DialogSaveBinding>() {

    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> DialogSaveBinding
        get() = DialogSaveBinding::inflate

    override fun getViewModelClass() = SaveDialogViewModel::class.java
    override fun getViewModelScope() = this

    lateinit var iconAdapter: IconAdapter

    override val bindViews: DialogSaveBinding.() -> Unit = {
        close.setOnClickListener {
            dismiss()
        }

        cancelButton.setOnClickListenerDebounce {
            dismiss()
        }

        iconText.text = viewmodel.getIconModel().iconResource.type

        saveButton.setOnClickListener {
            if(nameEdittext.text.toString().isEmpty())
                errorText.show()
            else {
                errorText.gone()
                viewmodel.insertSavedLoan(
                    model = GetSavedLoanModel(
                        name = binding.nameEdittext.text.toString(),
                        code = Random(100).toString(),
                        description = "sometext",
                        type = viewmodel.selectedType,
                        background = "",
                        src = "",
                        startDate = binding.dataText.text.toString(),
                        paidOff = calculatePaidOff(arguments?.getString(PERIOD)?.toInt() ?: 0, viewmodel.selectedStartDate),
                        loanAmount = arguments?.getString(AMOUNT),
                        interestRate = arguments?.getString(RATE),
                        compoundingFrequency = arguments?.getString(FREQUENCY),
                        period = arguments?.getString(PERIOD),
                        totalRePayment = arguments?.getString(PAYMENT),
                        termInMonth = arguments?.getInt(TERMINMONTH),
                        totalInterest = arguments?.getString(TOTALINTEREST),
                        totalPayment = arguments?.getString(TOTALPAYMENT)
                    )
                )
            }
        }

        calendar.setOnClickListenerDebounce {
            datePickerBottomSheet {
                onDateSelected {
                    updateSelectedDate(it)
                }
            }.show(childFragmentManager, DatePickerBottomSheet::class.java.canonicalName)
        }

        iconAdapter = IconAdapter(IconAdapter.IconItemClick{
            // handle click listener
            iconText.text = it.iconResource.type
            viewmodel.setIconModel(it)
        })
        viewmodel.getIconModelList(requireContext())

        recyclerViewIcon.adapter = iconAdapter
    }

    private fun updateSelectedDate(it: Date) {
        viewmodel.selectedStartDate = it
        binding.dataText.text = it.asFormattedDateWithDot()
        binding.dataText.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.color_pure_black
            )
        )
    }

    override fun onResume() {
        // Set the width of the dialog proportional to 90% of the screen width
        val window = dialog?.window
        val size = Point()
        val display = window?.windowManager?.defaultDisplay
        display?.getSize(size)
        window?.setLayout((size.x * 0.90).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.CENTER)
        super.onResume()
    }

    override fun observeEffect(effect: SaveDialogEffect) {
        when(effect){
            is SaveDialogEffect.InsertSavedLoan -> {
                toast("Your loan added to favorite.")
                dismiss()
                //viewmodel.navigate(NavigationCommand.Deeplink(DeeplinkNavigationTypes.SAVE_PAGE,null,false))
            }
        }
    }

    override fun observeState(state: SaveDialogState) {
        when(state){
            is SaveDialogState.ListOfIconModel -> {
                iconAdapter.submitList(state.list)
            }
        }
    }

    // Generate palette synchronously and return it.
    fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).generate()

    // Generate palette asynchronously and use it on a different thread using onGenerated().
    fun createPaletteAsync(bitmap: Bitmap) {
        Palette.from(bitmap).generate { palette ->
            // Use generated instance.
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(AMOUNT)?.let {

        }
        isCancelable = false
        updateSelectedDate(Date())
    }

    private fun getBackgroundColorPallet(@ColorInt color: Int): Int{
        val vibrantSwatch = createPaletteSync(BitmapFactory.decodeResource(resources, color)).vibrantSwatch
        return vibrantSwatch?.rgb?: ContextCompat.getColor(requireContext(),R.color.color_gray_two)
    }

    companion object {

        private const val AMOUNT = "amount"
        private const val PERIOD = "period"
        private const val RATE = "rate"
        private const val PAYMENT = "payment"
        private const val FREQUENCY = "frequency"
        private const val TERMINMONTH = "termInMonth"
        private const val TOTALINTEREST = "totalInterest"
        private const val TOTALPAYMENT = "totalPayment"

        fun build(amount: String? = null, period: String? = null, rate: String? = null, payment: String? = null, frequency: String? = null, termInMonth: Int? = null,totalInterest: String? = null,totalPayment: String? = null) = SaveDialog().apply {
            arguments =
                bundleOf(
                    AMOUNT to amount,
                    PERIOD to period,
                    RATE to rate,
                    PAYMENT to payment,
                    FREQUENCY to frequency,
                    TERMINMONTH to termInMonth,
                    TOTALINTEREST to totalInterest,
                    TOTALPAYMENT to totalPayment
                )
        }
    }
}