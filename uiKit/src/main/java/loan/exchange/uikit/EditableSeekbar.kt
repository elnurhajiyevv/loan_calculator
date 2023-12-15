package loan.exchange.uikit

import android.animation.ValueAnimator
import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.constraintlayout.widget.ConstraintLayout
import loan.exchange.common.extensions.gone
import loan.exchange.common.extensions.show
import loan.exchange.uikit.databinding.EditableSeekbarBinding
import kotlin.math.abs


class EditableSeekBar @JvmOverloads constructor
    (context: Context,
     attrs: AttributeSet? = null,
     defStyleAttr: Int = 0): ConstraintLayout(context, attrs, defStyleAttr), OnSeekBarChangeListener, TextWatcher, OnFocusChangeListener, EditableSeekbarEdittext.OnEditTextListener {

    private var selectOnFocus = false
    private var animateChanges = false
    private var seekBarAnimator: ValueAnimator? = null
    private var currentValue = 0
    private var minValue = 0
    private var maxValue = 100
    private var touching = false
    private var mListener: OnEditableSeekBarChangeListener? = null

    private var binding: EditableSeekbarBinding = EditableSeekbarBinding.inflate(
        LayoutInflater.from(context), this, true)


    interface OnEditableSeekBarChangeListener {
        fun onEditableSeekBarProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean)
        fun onStartTrackingTouch(seekBar: SeekBar?)
        fun onStopTrackingTouch(seekBar: SeekBar?)
        fun onEnteredValueTooHigh()
        fun onEnteredValueTooLow()
        fun onEditableSeekBarValueChanged(value: Int)
    }


    init {
        context.obtainStyledAttributes(attrs, R.styleable.EditableSeekBar, defStyleAttr, R.style.light_caption1).apply {
            try {
                setTitle(getString(R.styleable.EditableSeekBar_esbTitle))
                binding.loanTitle.setTextAppearance(
                    getContext(),
                    getResourceId(R.styleable.EditableSeekBar_esbTitleAppearance, 0)
                )
                selectOnFocus = getBoolean(R.styleable.EditableSeekBar_esbSelectAllOnFocus, true)
                animateChanges = getBoolean(R.styleable.EditableSeekBar_esbAnimateSeekBar, true)
                binding.loanEdittext.setSelectAllOnFocus(selectOnFocus)
                val min = getInteger(R.styleable.EditableSeekBar_esbMin, SEEKBAR_DEFAULT_MIN)
                val max = getInteger(R.styleable.EditableSeekBar_esbMax, SEEKBAR_DEFAULT_MAX)
                setRange(min, max)
                setValue(getInteger(
                    R.styleable.EditableSeekBar_esbValue,
                    translateToRealValue(range / 2)))
            } finally {
                recycle()
            }
        }
        binding.loanRate.setOnSeekBarChangeListener(this)
        binding.loanEdittext.addTextChangedListener(this)
        binding.loanEdittext.onFocusChangeListener = this
        binding.loanEdittext.setOnKeyboardDismissedListener(this)
        binding.loanRate.setOnTouchListener { v, event ->
            hideKeyboard()
            false
        }

    }

    private fun setEditTextWidth(width: Float) {
        val params: ViewGroup.LayoutParams = binding.loanEdittext.layoutParams
        params.width = width.toInt()
        binding.loanEdittext.layoutParams = params
    }

    /**
     * Set callback listener for changes of EditableSeekBar.
     * @param listener OnEditableSeekBarChangeListener
     */
    fun setOnEditableSeekBarChangeListener(listener: OnEditableSeekBarChangeListener?) {
        mListener = listener
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        currentValue = translateToRealValue(progress)
        if (fromUser) {
            setEditTextValue(currentValue)
            if (selectOnFocus) binding.loanEdittext.selectAll() else binding.loanEdittext.setSelection(
                binding.loanEdittext.text?.length?:0
            )
        }
        if (mListener != null) mListener?.onEditableSeekBarProgressChanged(
            seekBar,
            currentValue,
            fromUser
        )
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
        if (mListener != null) mListener?.onStartTrackingTouch(seekBar)
        touching = true
        binding.loanEdittext.requestFocus()
        if (selectOnFocus) binding.loanEdittext.selectAll() else binding.loanEdittext.setSelection(
            binding.loanEdittext.text?.length?:0
        )
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        if (mListener != null) mListener?.onStopTrackingTouch(seekBar)
        touching = false
        currentValue = translateToRealValue(seekBar.progress)
        if (mListener != null) mListener?.onEditableSeekBarValueChanged(currentValue)
    }

    override fun onEditTextKeyboardDismissed() {
        checkValue()
        if (mListener != null) mListener?.onEditableSeekBarValueChanged(currentValue)
    }

    override fun onEditTextKeyboardDone() {
        checkValue()
        if (mListener != null) mListener?.onEditableSeekBarValueChanged(currentValue)
        hideKeyboard()
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    override fun afterTextChanged(s: Editable) {
        if (touching) return
        if (s.toString().isNotEmpty() && isNumber(s.toString())) {
            var value = s.toString().toInt()
            if (value > maxValue && currentValue != maxValue) {
                value = maxValue
                setEditTextValue(value)
                if (selectOnFocus) binding.loanEdittext.selectAll() else binding.loanEdittext.setSelection(
                    binding.loanEdittext.text?.length?:1
                )
                if (mListener != null) {
                    mListener?.onEnteredValueTooHigh()
                    //                    mListener.onEditableSeekBarValueChanged(currentValue);
                }
            }
            if (value < minValue && currentValue != minValue) {
                value = minValue
                setEditTextValue(value)
                if (selectOnFocus) binding.loanEdittext.selectAll() else binding.loanEdittext.setSelection(
                    binding.loanEdittext.text?.length?:0
                )
                if (mListener != null) {
                    mListener?.onEnteredValueTooLow()
                    //                    mListener.onEditableSeekBarValueChanged(currentValue);
                }
            }
            if (value in minValue..maxValue && value != currentValue) {
                currentValue = value
                setSeekBarValue(translateFromRealValue(currentValue))
                if (mListener != null) mListener?.onEditableSeekBarValueChanged(currentValue)
            }
        } else {
//            currentValue = minValue;
//            setSeekBarValue(translateFromRealValue(currentValue));
        }
    }

    private fun checkValue() {
        setEditTextValue(currentValue)
    }

    private fun isNumber(s: String): Boolean {
        try {
            s.toInt()
        } catch (e: NumberFormatException) {
            return false
        }
        return true
    }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (v is EditText) {
            if (!hasFocus) {
                val sendValueChanged = binding.loanEdittext.text.toString().isEmpty() || !isNumber(
                    binding.loanEdittext.text.toString()
                ) || !isInRange(binding.loanEdittext.text.toString().toInt())
                if (sendValueChanged) {
                    checkValue()
                }
                if (mListener != null && sendValueChanged) mListener?.onEditableSeekBarValueChanged(
                    currentValue
                )
                //                    setEditTextValue(translateFromRealValue(currentValue));
            } else {
                if (selectOnFocus) binding.loanEdittext.selectAll() else binding.loanEdittext.setSelection(
                    binding.loanEdittext.text?.length?:0
                )
            }
        }
    }

    private fun hideKeyboard() {
        val imm =
            context.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(
            binding.loanEdittext.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    /**
     * Set title for EditableSeekBar. Title hidden if empty or null.
     * @param title String
     */
    fun setTitle(title: String?) {
        if (!title.isNullOrEmpty()) {
            binding.loanTitle.text = title
            binding.loanTitle.show()
        } else binding.loanTitle.gone()
    }

    /**
     * Set title color.
     * @param color integer
     */
    fun setTitleColor(color: Int) {
        binding.loanTitle.setTextColor(color)
    }

    private fun setEditTextValue(value: Int) {
        if (binding.loanEdittext != null) {
//            esbEditText.removeTextChangedListener(this);
            binding.loanEdittext.setText(value.toString())
            //            esbEditText.addTextChangedListener(this);
        }
    }

    private fun setSeekBarValue(value: Int) {
        if (binding.loanRate != null) {
            if (animateChanges) animateSeekbar(
                binding.loanRate.progress,
                value
            ) else binding.loanRate.progress =
                value
        }
    }

    private fun translateFromRealValue(realValue: Int): Int {
        return if (realValue < 0) abs(realValue - minValue) else realValue - minValue
    }

    private fun translateToRealValue(sbValue: Int): Int {
        return minValue + sbValue
    }

    /***
     * Set range for EditableSeekBar. Min value must be smaller than max value.
     * @param min integer
     * @param max integer
     */
    fun setRange(min: Int, max: Int) {
        if (min > max) {
            minValue = SEEKBAR_DEFAULT_MIN
            maxValue = SEEKBAR_DEFAULT_MAX
        } else {
            minValue = min
            maxValue = max
        }
        binding.loanRate.max = range
        if (!isInRange(currentValue)) {
            if (currentValue < minValue) currentValue = minValue
            if (currentValue > maxValue) currentValue = maxValue
            setValue(currentValue)
        }
    }

    val range: Int
        /**
         * Get range of EditableSeekBar.
         * @return integer - Absolute range
         */
        get() = if (maxValue < 0) Math.abs(maxValue - minValue) else maxValue - minValue

    private fun isInRange(value: Int): Boolean {
        if (value < minValue) {
            if (mListener != null) mListener?.onEnteredValueTooLow()
            return false
        }
        if (value > maxValue) {
            if (mListener != null) mListener?.onEnteredValueTooHigh()
            return false
        }
        return true
    }

    fun getValue() = currentValue

    fun setValue(value: Int) {
        var value = value
        if (!isInRange(value)) {
            if (value < minValue) value = minValue
            if (value > maxValue) value = maxValue
        }
        currentValue = value
        setEditTextValue(currentValue)
        setSeekBarValue(translateFromRealValue(currentValue))
    }

    /**
     * Set maximum value for EditableSeekBar.
     * @param max integer
     */
    fun setMaxValue(max: Int) {
        setRange(minValue, max)
    }

    /**
     * Set minimum value for EditableSeekBar.
     * @param min integer
     */
    fun setMinValue(min: Int) {
        setRange(min, maxValue)
    }

    /**
     * Enable or disable SeekBar animation on value change
     * @param animate true/false
     */
    fun setAnimateSeekBar(animate: Boolean) {
        animateChanges = animate
    }

    private class SavedState : BaseSavedState {
        var value = 0
        var focus = false
        var animate = false

        internal constructor(superState: Parcelable?) : super(superState)
        private constructor(`in`: Parcel) : super(`in`) {
            value = `in`.readInt()
            focus = `in`.readInt() == 1
            animate = `in`.readInt() == 1
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(value)
            out.writeInt(if (focus) 1 else 0)
            out.writeInt(if (animate) 1 else 0)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Creator<SavedState> {
            override fun createFromParcel(parcel: Parcel): SavedState {
                return SavedState(parcel)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }

    public override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        val ss = SavedState(superState)
        ss.value = currentValue
        ss.focus = selectOnFocus
        ss.animate = animateChanges
        return ss
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        val ss = state as SavedState
        super.onRestoreInstanceState(ss.superState)
        setValue(ss.value)
        animateChanges = ss.animate
        selectOnFocus = ss.focus
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>?) {
        super.dispatchFreezeSelfOnly(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>?) {
        super.dispatchThawSelfOnly(container)
    }

    private fun animateSeekbar(startValue: Int, endValue: Int) {
        if (seekBarAnimator != null && seekBarAnimator?.isRunning == true) seekBarAnimator?.cancel()
        if (seekBarAnimator == null) {
            seekBarAnimator = ValueAnimator.ofInt(startValue, endValue)
            seekBarAnimator?.interpolator = DecelerateInterpolator()
            seekBarAnimator?.duration = ANIMATION_DEFAULT_DURATION.toLong()
            seekBarAnimator?.addUpdateListener { animation ->
                binding.loanRate.progress =
                    (animation.animatedValue as Int)
            }
        } else seekBarAnimator?.setIntValues(startValue, endValue)
        seekBarAnimator?.start()
    }

    companion object {
        private const val SEEKBAR_DEFAULT_MAX = 100
        private const val SEEKBAR_DEFAULT_MIN = 0
        private const val EDITTEXT_DEFAULT_WIDTH = 50
        private const val EDITTEXT_DEFAULT_FONT_SIZE = 18
        private const val ANIMATION_DEFAULT_DURATION = 300
    }
}