package loan.calculator.uikit.textview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import loan.calculator.uikit.R
import java.util.WeakHashMap


/*
 * Created by Elnur on on 27.04.24, 18.
 * Copyright (c) 2024 . All rights reserved to Elnur.
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

class AutofitLayout : FrameLayout {
    private var mEnabled = false
    private var mMinTextSize = 0f
    private var mPrecision = 0f
    private val mHelpers: WeakHashMap<View, AutofitHelper> = WeakHashMap<View, AutofitHelper>()

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
        var sizeToFit = true
        var minTextSize = -1
        var precision = -1f
        if (attrs != null) {
            val ta: TypedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.AutofitTextView,
                defStyle,
                0
            )
            sizeToFit = ta.getBoolean(R.styleable.AutofitTextView_sizeToFit, sizeToFit)
            minTextSize = ta.getDimensionPixelSize(
                R.styleable.AutofitTextView_minTextSize,
                minTextSize
            )
            precision = ta.getFloat(R.styleable.AutofitTextView_precision, precision)
            ta.recycle()
        }
        mEnabled = sizeToFit
        mMinTextSize = minTextSize.toFloat()
        mPrecision = precision
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        super.addView(child, index, params)
        val textView = child as AppCompatTextView
        val helper: AutofitHelper = AutofitHelper.create(textView)
            .setEnabled(mEnabled)
        if (mPrecision > 0) {
            helper.setPrecision(mPrecision)
        }
        if (mMinTextSize > 0) {
            helper.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, mMinTextSize)
        }
        mHelpers[textView] = helper
    }

    /**
     * Returns the [AutofitHelper] for this child View.
     */
    fun getAutofitHelper(textView: TextView): AutofitHelper? {
        return mHelpers[textView]
    }

    /**
     * Returns the [AutofitHelper] for this child View.
     */
    fun getAutofitHelper(index: Int): AutofitHelper? {
        return mHelpers[getChildAt(index)]
    }
}