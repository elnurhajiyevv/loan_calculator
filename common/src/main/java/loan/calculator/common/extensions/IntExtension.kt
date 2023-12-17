package loan.calculator.common.extensions

import android.content.Context
import android.content.res.Resources
import androidx.core.content.ContextCompat

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Float.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

fun Int.asColorResource(context: Context) = ContextCompat.getColor(context, this)

fun Int.asDrawableResource(context: Context) = ContextCompat.getDrawable(context, this)

fun Int.asDimenResource(context: Context) = context.resources.getDimension(this)
