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
import loan.calculator.uikit.R
import loan.calculator.loan.databinding.DialogFragmentPopupBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


class PopUpBottomSheet : BaseNotSerializableBottomSheet() {

    private var onOkButtonClicked: (() -> Unit)? = null
    private var onCloseButtonClicked: (() -> Unit)? = null
    private var onDismiss: (() -> Unit)? = null

    override var showFullscreen: Boolean
        get() = false
        set(value) {}


    private lateinit var binding: DialogFragmentPopupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentPopupBinding.inflate(inflater, container, false)
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
                solidColor = R.color.adapter_background,
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
            binding.okButton.setOnClickListener {
                onOkButtonClicked?.invoke()
                this@PopUpBottomSheet.dismiss()
            }
            binding.close.setOnClickListener {
                onCloseButtonClicked?.invoke()
                this@PopUpBottomSheet.dismiss()
            }
        }
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
        var onOkButtonClicked: (() -> Unit)? = null
        var onCloseButtonClicked: (() -> Unit)? = null
        var onDismiss: (() -> Unit)? = null

        fun onOkButtonClicked(onOkButtonClicked: () -> Unit) {
            this.onOkButtonClicked = onOkButtonClicked
        }

        fun onCloseButtonClicked(onCloseButtonClicked: () -> Unit) {
            this.onCloseButtonClicked = onCloseButtonClicked
        }

        fun onDismiss(onDismiss: () -> Unit) {
            this.onDismiss = onDismiss
        }

        fun build(): PopUpBottomSheet {
            val bottomSheet = PopUpBottomSheet()
            bottomSheet.onOkButtonClicked = onOkButtonClicked
            bottomSheet.onCloseButtonClicked = onCloseButtonClicked
            bottomSheet.onDismiss = onDismiss
            return bottomSheet
        }
    }


}

fun popUpBottomSheet(lambda: PopUpBottomSheet.Builder.() -> Unit) =
    PopUpBottomSheet.Builder().apply(lambda).build()
