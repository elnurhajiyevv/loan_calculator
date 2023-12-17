package loan.calculator.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import loan.calculator.common.library.analytics.bindAnalytics
import loan.calculator.core.R
import loan.calculator.core.databinding.FragmentBottomSheetDialogBaseBinding
import loan.calculator.core.delegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BaseFragmentBottomSheetDialog : BottomSheetDialogFragment() {

    val binding by viewBinding(FragmentBottomSheetDialogBaseBinding::bind)

    private var title: Int = R.string.unknown_error_title
    private var text: Int = R.string.unknown_error_text
    private var textMessage: String? = null

    private var canCancel: Boolean = true
    private var buttonText: Int = R.string.close
    private var action: (BaseFragmentBottomSheetDialog) -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindAnalytics(
            getScreenName = { getString(title) },
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_bottom_sheet_dialog_base, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.title.setText(title)

        if (textMessage != null && textMessage != "")
            binding.text.text = textMessage
        else
            binding.text.setText(text)


        binding.button.setText(buttonText)
        binding.button.setOnClickListener {
            action(this)
        }
        isCancelable = canCancel
    }

    companion object {
        fun build(
            title: Int = R.string.unknown_error_title,
            text: Int = R.string.unknown_error_text,
            textMessage: String? = null,
            cancelable: Boolean = true,
            buttonText: Int = R.string.close,
            action: (BaseFragmentBottomSheetDialog) -> Unit = {}
        ): BaseFragmentBottomSheetDialog {
            return BaseFragmentBottomSheetDialog().apply {
                this.title = title
                this.textMessage = textMessage
                this.text = text
                this.canCancel = cancelable
                this.buttonText = buttonText
                this.action = action
            }
        }
    }
}