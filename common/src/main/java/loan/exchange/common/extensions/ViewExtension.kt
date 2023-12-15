package loan.exchange.common.extensions

import android.graphics.drawable.GradientDrawable
import android.os.SystemClock
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun View.setPaddingHorizontal(padding: Int) {
    setPadding(padding, paddingTop, padding, paddingBottom)
}

fun View.setPaddingVertical(padding: Int) {
    setPadding(paddingLeft, padding, paddingRight, padding)
}

fun View.addCorners(@ColorRes solidColor: Int? = null, radius: FloatArray? = null) {
    val border = GradientDrawable()
    solidColor?.let { border.setColor(ContextCompat.getColor(context, solidColor)) }
    radius?.let { border.cornerRadii = radius }
    background = border
}

fun View.addBorder(@ColorRes solidColor: Int? = null, @ColorRes strokeColor: Int? = null, radius: Float? = null, strokeWidth: Int = 0) {
    val border = GradientDrawable()
    solidColor?.let { border.setColor(ContextCompat.getColor(context, solidColor)) }
    strokeColor?.let { border.setStroke(strokeWidth, ContextCompat.getColor(context, strokeColor)) }
    radius?.let { border.cornerRadius = radius }
    background = border
}

fun View.gone() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.setOnClickListenerDebounce(debounceTime: Long = 1000L, action: () -> Unit): View.OnClickListener {
    val click = object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action()

            lastClickTime = SystemClock.elapsedRealtime()
        }
    }
    this.setOnClickListener(click)
    return click
}
