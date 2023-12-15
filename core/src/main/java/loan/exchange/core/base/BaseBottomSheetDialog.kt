package loan.exchange.core.base

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import loan.exchange.common.library.analytics.bindAnalytics
import loan.exchange.common.extensions.addCorners
import loan.exchange.common.extensions.dp
import loan.exchange.core.R
import loan.exchange.core.extension.hideKeyboard
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.imageview.ShapeableImageView

abstract class BaseBottomSheetDialog() : BottomSheetDialogFragment() {

    open var showFullscreen: Boolean = true
    open var isDraggable: Boolean = true
    open var notFullscreen: () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Theme_Exchange_BottomSheetDialog)
        bindAnalytics(
            getScreenName = { null },
        )
    }

    override fun getTheme(): Int  = R.style.Theme_Exchange_BottomSheetDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                if (showFullscreen) {
                    setupFullHeight(it)
                    behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                }
                behaviour.isDraggable = isDraggable

            }
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val behavior = BottomSheetBehavior.from(view.parent as View)
        behavior.skipCollapsed = true
        if (!showFullscreen) {
            notFullscreen()
        }
    }

    fun makeRoundedCorners(view: View) {
        view.addCorners(
            solidColor = R.color.color_pure_white, radius = floatArrayOf(
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
    }

    /**
     * Creates root ViewGroup as FrameLayout, that contains background
     */
    fun defaultBackground(): ViewGroup {
        //root layout
        val radiuses = floatArrayOf(
            24.dp.toFloat(),
            24.dp.toFloat(),
            24.dp.toFloat(),
            24.dp.toFloat(),
            0f,
            0f,
            0f,
            0f
        )


        val layout = FrameLayout(requireContext())
        val layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        layout.layoutParams = layoutParams
        layout.addCorners(
            solidColor = R.color.color_pure_white,
            radius = radiuses
        )

        //gradient bottom
        val bottomGradientLayout = View(requireContext())
        bottomGradientLayout.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_main_bottom_gradient)
        bottomGradientLayout.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            120.dp,
            Gravity.BOTTOM
        )
        layout.addView(bottomGradientLayout)

        //gradient top
        val topGradientLayout = ShapeableImageView(requireContext())
        topGradientLayout.shapeAppearanceModel = topGradientLayout.shapeAppearanceModel
            .toBuilder()
            .setAllCornerSizes(24.dp.toFloat())
            .build()
        val topGradient = ContextCompat.getDrawable(requireContext(), R.drawable.bg_main_bottom_gradient)
        topGradientLayout.background = topGradient
        topGradientLayout.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            260.dp,
            Gravity.TOP
        )
        layout.addView(topGradientLayout)
        return layout
    }

    override fun onDestroy() {
        super.onDestroy()
        view?.hideKeyboard()
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

}