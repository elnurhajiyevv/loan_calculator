package loan.calculator.uikit.textview

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import loan.calculator.uikit.textview.AutofitHelper.OnTextSizeChangeListener


/*
 * Created by Elnur on on 27.04.24, 18.
 * Copyright (c) 2024 . All rights reserved to Elnur.
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */


class AutofitTextView : AppCompatTextView, OnTextSizeChangeListener {
    /**
     * Returns the [AutofitHelper] for this View.
     */
    var autofitHelper: AutofitHelper? = null
        private set

    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context, attrs, defStyle)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        autofitHelper = AutofitHelper.create(this, attrs, defStyle)
            .addOnTextSizeChangeListener(this)
    }
    // Getters and Setters
    /**
     * {@inheritDoc}
     */
    override fun setTextSize(unit: Int, size: Float) {
        super.setTextSize(unit, size)
        if (autofitHelper != null) {
            autofitHelper!!.setTextSize(unit, size)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun setLines(lines: Int) {
        super.setLines(lines)
        if (autofitHelper != null) {
            autofitHelper!!.setMaxLines(lines)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun setMaxLines(maxLines: Int) {
        super.setMaxLines(maxLines)
        if (autofitHelper != null) {
            autofitHelper!!.setMaxLines(maxLines)
        }
    }

    var isSizeToFit: Boolean
        /**
         * Returns whether or not the text will be automatically re-sized to fit its constraints.
         */
        get() = autofitHelper!!.isEnabled
        /**
         * If true, the text will automatically be re-sized to fit its constraints; if false, it will
         * act like a normal TextView.
         *
         * @param sizeToFit
         */
        set(sizeToFit) {
            autofitHelper!!.setEnabled(sizeToFit)
        }

    /**
     * Sets the property of this field (sizeToFit), to automatically resize the text to fit its
     * constraints.
     */
    fun setSizeToFit() {
        isSizeToFit = true
    }

    var maxTextSize: Float
        /**
         * Returns the maximum size (in pixels) of the text in this View.
         */
        get() = autofitHelper!!.maxTextSize
        /**
         * Set the maximum text size to the given value, interpreted as "scaled pixel" units. This size
         * is adjusted based on the current density and user font size preference.
         *
         * @param size The scaled pixel size.
         *
         * @attr ref android.R.styleable#TextView_textSize
         */
        set(size) {
            autofitHelper!!.setMaxTextSize(size)
        }

    /**
     * Set the maximum text size to a given unit and value. See TypedValue for the possible
     * dimension units.
     *
     * @param unit The desired dimension unit.
     * @param size The desired size in the given units.
     *
     * @attr ref android.R.styleable#TextView_textSize
     */
    fun setMaxTextSize(unit: Int, size: Float) {
        autofitHelper!!.setMaxTextSize(unit, size)
    }

    val minTextSize: Float
        /**
         * Returns the minimum size (in pixels) of the text in this View.
         */
        get() = autofitHelper!!.minTextSize

    /**
     * Set the minimum text size to the given value, interpreted as "scaled pixel" units. This size
     * is adjusted based on the current density and user font size preference.
     *
     * @param minSize The scaled pixel size.
     *
     * @attr ref me.grantland.R.styleable#AutofitTextView_minTextSize
     */
    fun setMinTextSize(minSize: Int) {
        autofitHelper!!.setMinTextSize(TypedValue.COMPLEX_UNIT_SP, minSize.toFloat())
    }

    /**
     * Set the minimum text size to a given unit and value. See TypedValue for the possible
     * dimension units.
     *
     * @param unit The desired dimension unit.
     * @param minSize The desired size in the given units.
     *
     * @attr ref me.grantland.R.styleable#AutofitTextView_minTextSize
     */
    fun setMinTextSize(unit: Int, minSize: Float) {
        autofitHelper!!.setMinTextSize(unit, minSize)
    }

    var precision: Float
        /**
         * Returns the amount of precision used to calculate the correct text size to fit within its
         * bounds.
         */
        get() = autofitHelper!!.precision
        /**
         * Set the amount of precision used to calculate the correct text size to fit within its
         * bounds. Lower precision is more precise and takes more time.
         *
         * @param precision The amount of precision.
         */
        set(precision) {
            autofitHelper!!.setPrecision(precision)
        }

    override fun onTextSizeChange(textSize: Float, oldTextSize: Float) {
        // do nothing
    }
}