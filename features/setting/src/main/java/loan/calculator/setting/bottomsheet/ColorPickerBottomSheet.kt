package loan.calculator.setting.bottomsheet

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import loan.calculator.common.extensions.addCorners
import loan.calculator.common.extensions.dp
import loan.calculator.core.base.BaseNotSerializableBottomSheet
import loan.calculator.setting.adapter.ColorChoiceAdapter
import loan.calculator.setting.databinding.DialogColorPickerBinding
import loan.calculator.uikit.R

class ColorPickerBottomSheet : BaseNotSerializableBottomSheet() {

    private var onYes: ((selectedIndex: Int) -> Unit)? = null
    private var onNo: (() -> Unit)? = null
    private var onDismiss: (() -> Unit)? = null
    private var initialSelectedIndex: Int = -1

    private lateinit var binding: DialogColorPickerBinding
    private var selectedIndex: Int = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogColorPickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override var showFullscreen: Boolean
        get() = false
        set(value) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (dialog as? BottomSheetDialog)?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED
        initView()
    }

    private fun initView() = with(binding) {
        mainLayout.addCorners(
            solidColor = R.color.adapter_background,
            radius = floatArrayOf(24.dp.toFloat(), 24.dp.toFloat(), 24.dp.toFloat(), 24.dp.toFloat(), 0f, 0f, 0f, 0f),
        )

        val themeColors = listOf(
            R.color.theme_one,
            R.color.theme_two,
            R.color.theme_three,
            R.color.theme_four,
            R.color.theme_five,
            R.color.theme_six,
            R.color.theme_seven,
            R.color.theme_eight,
            R.color.theme_nine,
            R.color.theme_ten,
        )

        selectedIndex = initialSelectedIndex.coerceIn(-1, themeColors.lastIndex)

        colorsRecycler.layoutManager = GridLayoutManager(requireContext(), 5)
        colorsRecycler.adapter = ColorChoiceAdapter(
            colors = themeColors,
            selectedIndex = selectedIndex,
            onSelected = { idx -> selectedIndex = idx },
        )



        yesButton.setOnClickListener {
            onYes?.invoke(selectedIndex)
            dismiss()
        }

        noButton.setOnClickListener {
            onNo?.invoke()
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss?.invoke()
    }

    class Builder {
        var initialSelectedIndex: Int = -1
        var onYes: ((selectedIndex: Int) -> Unit)? = null
        var onNo: (() -> Unit)? = null
        var onDismiss: (() -> Unit)? = null

        fun build(): ColorPickerBottomSheet {
            val bs = ColorPickerBottomSheet()
            bs.initialSelectedIndex = initialSelectedIndex
            bs.onYes = onYes
            bs.onNo = onNo
            bs.onDismiss = onDismiss
            return bs
        }
    }
}

fun colorPickerBottomSheet(lambda: ColorPickerBottomSheet.Builder.() -> Unit) =
    ColorPickerBottomSheet.Builder().apply(lambda).build()

