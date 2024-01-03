package loan.calculator.uikit.button

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import loan.calculator.common.extensions.isNotNull
import loan.calculator.domain.entity.enum.ButtonStyleEnum.*
import loan.calculator.domain.entity.enum.ButtonStyleEnum
import loan.calculator.uikit.R

class LoanButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialButton(context, attrs, defStyleAttr) {

    private var buttonTitle: String? = null
    private var buttonSpecialType: Int? = null

    init {
        if(!isInEditMode){
            context.obtainStyledAttributes(attrs, R.styleable.LoanButton, 0, 0).apply {
                buttonTitle = getString(R.styleable.LoanButton_android_text)
                buttonSpecialType = getInt(R.styleable.LoanButton_button_special_type, -1)
                isEnabled = getBoolean(R.styleable.LoanButton_android_enabled,true)
                recycle()
                setItemInfo()
            }
        }
    }

    private fun setItemInfo() {
        setText(buttonTitle)
    }

    fun setText(title: String?) {
        text = title
    }

    /**
     * Apply styles programmatically using [ButtonStyleEnum]
     */
    fun applyStyle(style: ButtonStyleEnum) {
        when (style) {
            PRIMARY_FILLED_LARGE -> {
                setStyle(
                    R.drawable.button_primary_filled_background,
                    R.color.primary_filled_text_color,
                    LoanButtonHeightEnum.LARGE.value,
                    resources.getDimensionPixelSize(R.dimen._16sdp)
                )
            }
            PRIMARY_FILLED_MEDIUM -> {
                setStyle(
                    R.drawable.button_primary_filled_background,
                    R.color.primary_filled_text_color,
                    LoanButtonHeightEnum.MEDIUM.value,
                    resources.getDimensionPixelSize(R.dimen._14sdp)
                )
            }
            PRIMARY_FILLED_SMALL -> {
                setStyle(
                    R.drawable.button_primary_filled_background,
                    R.color.primary_filled_text_color,
                    LoanButtonHeightEnum.SMALL.value,
                    resources.getDimensionPixelSize(R.dimen._12sdp)
                )
            }
            PRIMARY_OUTLINED_LARGE -> {
                setStyle(
                    R.drawable.button_primary_outlined_background,
                    R.color.primary_outlined_text_color,
                    LoanButtonHeightEnum.LARGE.value,
                    resources.getDimensionPixelSize(R.dimen._16sdp)
                )
            }
            PRIMARY_OUTLINED_MEDIUM -> {
                setStyle(
                    R.drawable.button_primary_outlined_background,
                    R.color.primary_outlined_text_color,
                    LoanButtonHeightEnum.MEDIUM.value,
                    resources.getDimensionPixelSize(R.dimen._14sdp)
                )
            }
            PRIMARY_OUTLINED_SMALL -> {
                setStyle(
                    R.drawable.button_primary_outlined_background,
                    R.color.primary_outlined_text_color,
                    LoanButtonHeightEnum.SMALL.value,
                    resources.getDimensionPixelSize(R.dimen._12sdp)
                )
            }
            DELETE_FILLED_LARGE -> {
                setStyle(
                    R.drawable.button_delete_filled_background,
                    R.color.delete_filled_text_color,
                    LoanButtonHeightEnum.LARGE.value,
                    resources.getDimensionPixelSize(R.dimen._12sdp)
                )
            }
            DELETE_FILLED_MEDIUM -> {
                setStyle(
                    R.drawable.button_delete_filled_background,
                    R.color.delete_filled_text_color,
                    LoanButtonHeightEnum.MEDIUM.value,
                    resources.getDimensionPixelSize(R.dimen._12sdp)
                )
            }
            DELETE_FILLED_SMALL -> {
                setStyle(
                    R.drawable.button_delete_filled_background,
                    R.color.delete_filled_text_color,
                    LoanButtonHeightEnum.SMALL.value,
                    resources.getDimensionPixelSize(R.dimen._10sdp)
                )
            }
            DELETE_OUTLINED_LARGE -> {
                setStyle(
                    R.drawable.button_delete_outlined_background,
                    R.color.delete_outlined_text_color,
                    LoanButtonHeightEnum.LARGE.value,
                    resources.getDimensionPixelSize(R.dimen._12sdp)
                )
            }
            DELETE_OUTLINED_MEDIUM -> {
                setStyle(
                    R.drawable.button_delete_outlined_background,
                    R.color.delete_outlined_text_color,
                    LoanButtonHeightEnum.MEDIUM.value,
                    resources.getDimensionPixelSize(R.dimen._12sdp)
                )
            }
            DELETE_OUTLINED_SMALL -> {
                setStyle(
                    R.drawable.button_delete_outlined_background,
                    R.color.delete_outlined_text_color,
                    LoanButtonHeightEnum.SMALL.value,
                    resources.getDimensionPixelSize(R.dimen._10sdp)
                )
            }
        }
    }

    /**
     * Set style configurations according to selected style and size
     */
    private fun setStyle(
        buttonStyle: Int,
        buttonTextColor: Int,
        buttonHeight: Int,
        buttonTextSize: Int
    ) {
        /**
         * Set background drawable using background styles
         */
        setBackgroundResource(buttonStyle)
        /**
         * Set text colors using
         */
        setTextColor(ContextCompat.getColorStateList(context, buttonTextColor))
        /**
         * Set button height using screen size with [LoanButtonHeightEnum]
         */
        layoutParams.height =
            (buttonHeight * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
        /**
         * Set button text size using dynamic dimensions
         */
        textSize =
            buttonTextSize / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

        /**
         * Set common configurations
         */
        letterSpacing = 0f
        isAllCaps = false
    }

    /**
     * Button heights
     */
    enum class LoanButtonHeightEnum(val value: Int) {
        LARGE(64),
        MEDIUM(48),
        SMALL(40)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)

        if (buttonSpecialType.isNotNull()) {
            when (buttonSpecialType) {
                0 -> {
                    icon = if(enabled){
                        ContextCompat.getDrawable(context,R.drawable.ic_home)
                    } else{
                        ContextCompat.getDrawable(context, R.drawable.ic_calc)
                    }
                }
                1 -> {
                    icon = if(enabled){
                        ContextCompat.getDrawable(context,R.drawable.ic_arrow_left)
                    } else{
                        ContextCompat.getDrawable(context, R.drawable.ic_arrow_left)
                    }

                }
            }
        }
    }
}

