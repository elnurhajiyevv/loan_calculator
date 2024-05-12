package loan.calculator.save.bottomsheet

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import loan.calculator.common.extensions.addCorners
import loan.calculator.common.extensions.dp
import loan.calculator.core.base.BaseNotSerializableBottomSheet
import loan.calculator.uikit.R
import java.util.*
import loan.calculator.save.databinding.DialogBottomSheetBinding

class DialogBottomSheet: BaseNotSerializableBottomSheet() {

    private var backButton = false
    private var onOkButtonClicked: (() -> Unit)? = null
    private var onDismiss: (() -> Unit)? = null
    private var onBack: (() -> Unit)? = null

    override var showFullscreen: Boolean
        get() = false
        set(value) {}


    private lateinit var binding: DialogBottomSheetBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogBottomSheetBinding.inflate(inflater, container, false)
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
                radius = floatArrayOf(24.dp.toFloat(), 24.dp.toFloat(), 24.dp.toFloat(), 24.dp.toFloat(), 0f, 0f, 0f, 0f)
            )

            binding.okButton.setOnClickListener {
                onOkButtonClicked?.invoke()
                this@DialogBottomSheet.dismiss()
            }

            binding.cancelButton.setOnClickListener {
                this@DialogBottomSheet.dismiss()
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
        var onDismiss: (() -> Unit)? = null
        var onBack: (() -> Unit)? = null

        fun onOkButtonClicked(onOkButtonClicked: () -> Unit) {
            this.onOkButtonClicked = onOkButtonClicked
        }

        fun onDismiss(onDismiss: () -> Unit) {
            this.onDismiss = onDismiss
        }
        fun onBack(onDismiss: () -> Unit) {
            this.onBack = onBack
        }

        fun build(): DialogBottomSheet? {
            val bottomSheet = DialogBottomSheet()
            bottomSheet.onOkButtonClicked = onOkButtonClicked
            bottomSheet.onDismiss = onDismiss
            bottomSheet.onBack = onBack
            return bottomSheet
        }
    }


}
fun dialogBottomSheet(lambda: DialogBottomSheet.Builder.() -> Unit) =
    DialogBottomSheet.Builder().apply(lambda).build()
