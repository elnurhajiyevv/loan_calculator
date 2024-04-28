package loan.calculator.uikit.toolbar

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import loan.calculator.common.extensions.getString
import loan.calculator.common.extensions.gone
import loan.calculator.common.extensions.show
import loan.calculator.uikit.R
import loan.calculator.uikit.databinding.LoanToolbarBinding

class LoanToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding: LoanToolbarBinding =
        LoanToolbarBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    private var mOnToolbarLeftActionClick: (() -> Unit)? = null
    private var mOnToolbarRightActionClick: (() -> Unit)? = null
    private var mOnToolbarRightActionDeleteClick: (() -> Unit)? = null

    private var title: String = ""
    private var titleStyle: Int = 0
    private var toolbarOption: Int = LoanToolbarOption.WITH_LEFT_AND_RIGHT.value
    private var toolbarRightActionIcon: Int = R.drawable.ic_saved
    private var toolbarRightActionDeleteIcon: Int = R.drawable.ic_delete
    private var toolbarLeftActionIcon: Int = R.drawable.ic_remove

    var navigationIcon: Drawable?
        get() {
            return binding.root.navigationIcon
        }
        set(value) {
            binding.root.navigationIcon = value
        }

    init {
        if(!isInEditMode){
            tag = "toolbar"
            context.obtainStyledAttributes(
                attrs,
                R.styleable.LoanToolbar,
                defStyleAttr,
                R.style.LoanToolbarStyle
            ).apply {
                try {
                    toolbarOption = getInteger(
                        R.styleable.LoanToolbar_loan_toolbar_option,
                        LoanToolbarOption.WITH_LEFT_AND_RIGHT.value
                    )
                    title = getString(R.styleable.LoanToolbar_loan_toolbar_title).toString()
                    titleStyle = getInt(R.styleable.LoanToolbar_android_textStyle,0)
                    toolbarLeftActionIcon = getResourceId(
                        R.styleable.LoanToolbar_loan_toolbar_left_action_icon,
                        R.drawable.ic_remove
                    )
                    toolbarRightActionIcon = getResourceId(
                        R.styleable.LoanToolbar_loan_toolbar_right_action_icon,
                        R.drawable.ic_saved
                    )
                    toolbarRightActionDeleteIcon = getResourceId(
                        R.styleable.LoanToolbar_loan_toolbar_delete_action_icon,
                        R.drawable.ic_delete
                    )
                } finally {
                    recycle()
                }
            }
            with(binding) {
                root.navigationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_arrow_left)
                setTitle(title)
                setTitleStyle(titleStyle)
                setToolbarActionIcon(toolbarOption)
                setToolbarRightActionButtonIcon(toolbarRightActionIcon)
                setToolbarRightActionDeleteButtonIcon(toolbarRightActionDeleteIcon)
                setToolbarLeftActionButtonIcon(toolbarLeftActionIcon)
                loanToolbarLeft.setOnClickListener {
                    mOnToolbarLeftActionClick?.invoke()
                }
                loanToolbarRight.setOnClickListener {
                    mOnToolbarRightActionClick?.invoke()
                }
                loanToolbarDelete.setOnClickListener {
                    mOnToolbarRightActionDeleteClick?.invoke()
                }
            }
        }
    }

    private fun setToolbarLeftActionButtonIcon(@DrawableRes resId: Int) {
        this.toolbarLeftActionIcon = resId
        binding.loanToolbarLeft.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                toolbarLeftActionIcon
            )
        )
    }

    private fun setToolbarRightActionButtonIcon(@DrawableRes resId: Int) {
        this.toolbarRightActionIcon = resId
        binding.loanToolbarRight.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                toolbarRightActionIcon
            )
        )
    }

    private fun setToolbarRightActionDeleteButtonIcon(@DrawableRes resId: Int) {
        this.toolbarRightActionDeleteIcon = resId
        binding.loanToolbarDelete.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                toolbarRightActionDeleteIcon
            )
        )
    }

    fun setToolbarLeftActionClick(actionClick: () -> Unit) {
        mOnToolbarLeftActionClick = actionClick
    }

    fun setGravityLeft(){
        binding.loanToolbarTitle.gravity = Gravity.LEFT
    }
    fun setToolbarRightActionClick(actionClick: () -> Unit) {
        mOnToolbarRightActionClick = actionClick
    }

    fun setToolbarRightDeleteActionClick(actionClick: () -> Unit) {
        mOnToolbarRightActionDeleteClick = actionClick
    }

    fun getTitle(): String {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
        binding.loanToolbarTitle.text = this.title
    }


    fun setTitleStyle(style: Int) {
        this.titleStyle = style
        binding.loanToolbarTitle.setTypeface(binding.loanToolbarTitle.typeface, style)
    }

    fun setTitle(resId: Int) {
        this.title = getString(resId)
        binding.loanToolbarTitle.text = this.title
    }

    fun showRightToolbar(on:Boolean){
        if(on){
            binding.loanToolbarRight.show()
            binding.loanToolbarDelete.show()
        } else {
            binding.loanToolbarRight.gone()
            binding.loanToolbarDelete.gone()
        }
    }

    fun setBackButtonVisibility(show: Boolean){
        with(binding.root){
            navigationIcon = if(show){
                ContextCompat.getDrawable(context, R.drawable.ic_arrow_left)
            }else{
                null
            }
        }
    }

    fun setToolbarActionIcon(toolbarAction: Int) {
        this.toolbarOption = toolbarAction
        when (this.toolbarOption) {
            LoanToolbarOption.WITH_LEFT.value -> {
                binding.loanToolbarLeft.show()
                binding.loanToolbarRight.gone()
                binding.loanToolbarDelete.gone()
            }
            LoanToolbarOption.WITH_RIGHT.value -> {
                binding.loanToolbarLeft.gone()
                binding.loanToolbarRight.show()
                binding.loanToolbarDelete.gone()
            }
            LoanToolbarOption.WITHOUT_LEFT_AND_RIGHT.value -> {
                binding.loanToolbarLeft.gone()
                binding.loanToolbarRight.gone()
                binding.loanToolbarDelete.gone()
            }
            LoanToolbarOption.WITH_LEFT_AND_RIGHT.value -> {
                binding.loanToolbarLeft.show()
                binding.loanToolbarRight.show()
                binding.loanToolbarDelete.gone()
            }
            LoanToolbarOption.WITH_FULL.value -> {
                binding.loanToolbarLeft.show()
                binding.loanToolbarRight.show()
                binding.loanToolbarDelete.show()
            }
            LoanToolbarOption.WITH_FULL_RIGHT.value -> {
                binding.loanToolbarLeft.gone()
                binding.loanToolbarRight.show()
                binding.loanToolbarDelete.show()
            }
        }
    }

    fun setToolbarRightIcon(resId: Int) {
        binding.loanToolbarRight.setImageResource(resId)
    }

    fun setToolbarLeftIcon(resId: Int) {
        binding.loanToolbarLeft.setImageResource(resId)
    }

    fun setNavigationOnClickListener(listener: (() -> Unit)?) {
        binding.root.setNavigationOnClickListener {
            listener?.invoke()
        }
    }

    /** Provide tint to all elements of toolbar - all buttons and texts
     * @param color is ColorInt
     */
    fun setTintToAllElements(@ColorInt color: Int) {
        binding.apply {
            loanToolbarTitle.setTextColor(color)

            loanToolbarLeft.apply {
                drawable?.let {
                    val newDrawable = it.mutate()
                    newDrawable.setTint(color)
                    setImageDrawable(newDrawable)
                }
            }

            loanToolbarRight.apply {
                drawable?.let {
                    val newDrawable = it.mutate()
                    newDrawable.setTint(color)
                    setImageDrawable(newDrawable)
                }
            }

            val navIcon = navigationIcon?.mutate()
            navIcon?.setTint(color)
            navigationIcon = navIcon

        }
    }

    enum class LoanToolbarOption(val value: Int) {
        WITH_LEFT_AND_RIGHT(0), WITH_LEFT(1),WITH_RIGHT(2), WITH_FULL(3),WITH_FULL_RIGHT(4),WITHOUT_LEFT_AND_RIGHT(5),
    }

}