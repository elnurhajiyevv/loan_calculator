package loan.calculator.loan.view

import android.graphics.Point
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import loan.calculator.common.extensions.asFormattedDateWithDot
import loan.calculator.common.extensions.setOnClickListenerDebounce
import loan.calculator.core.delegate.viewBinding
import loan.calculator.domain.entity.saved.GetSavedLoanModel
import loan.calculator.domain.entity.unit.IconModel
import loan.calculator.loan.R
import loan.calculator.loan.adapter.IconAdapter
import loan.calculator.loan.bottomsheet.DatePickerBottomSheet
import loan.calculator.loan.bottomsheet.datePickerBottomSheet
import loan.calculator.loan.databinding.DialogSaveBinding
import loan.calculator.loan.viewmodel.SaveDialogViewModel

@AndroidEntryPoint
class SaveDialog: DialogFragment() {

    lateinit var iconAdapter: IconAdapter

    private val binding by viewBinding(DialogSaveBinding::bind)
    private val saveDialogViewModel: SaveDialogViewModel by viewModels()
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_save, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*arguments?.getString(TITLE)?.let {
            binding.title.text = it
        }*/
        isCancelable = false

        binding.close.setOnClickListener {
            dismiss()
        }

        binding.cancelButton.setOnClickListenerDebounce {
            dismiss()
        }

        binding.saveButton.setOnClickListener {
            saveDialogViewModel.insertSavedLoan(GetSavedLoanModel("test","telebe","description1"))
        }

        binding.calendar.setOnClickListenerDebounce {
            datePickerBottomSheet {
                onDateSelected {
                    binding.dataText.text = it.asFormattedDateWithDot()
                    binding.dataText.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.color_pure_black
                        )
                    )
                }
            }.show(childFragmentManager, DatePickerBottomSheet::class.java.canonicalName)
        }

        iconAdapter = IconAdapter(IconAdapter.IconItemClick{
            // handle click listener
        })
        iconAdapter.submitList(getIconList())

        binding.recyclerViewIcon.adapter = iconAdapter
    }

    private fun getIconList(): List<IconModel>{
        var list = arrayListOf<IconModel>()
        list.add(IconModel("home",""))
        list.add(IconModel("car",""))
        list.add(IconModel("laptop",""))
        list.add(IconModel("phone",""))
        list.add(IconModel("card",""))
        return list
    }

    companion object {
        private const val TITLE = "title"
        private const val TEXT = "text"

        fun build(title: String? = null, text: String? = null) = SaveDialog().apply {
            arguments = bundleOf(TITLE to title, TEXT to text)
        }
    }
}