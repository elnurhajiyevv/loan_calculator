package loan.calculator.uikit.util

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginLeft
import loan.calculator.common.extensions.asFormattedDateWithDot
import loan.calculator.common.library.util.DateFormats
import java.util.Date

fun setBackgroundResources(resource: Int, vararg views : View){
    views.forEach {
        it.setBackgroundResource(resource)
    }
}

fun setBackgroundColor(color: Int, vararg views : View){
    views.forEach {
        it.setBackgroundColor(color)
    }
}

fun setImageResources(resource: Int, vararg views : ImageView){
    views.forEach {
        it.setImageResource(resource)
    }
}

fun disableSelection(vararg viewEditText : EditText, setSelection: Boolean) {
    viewEditText.forEach {
        it.isFocusable = setSelection
        it.isEnabled = setSelection
        it.isFocusableInTouchMode = setSelection
    }
}

fun resetLeftMargin(vararg views : View) {
    views.forEach {
        val params = it.layoutParams as ConstraintLayout.LayoutParams
        params.leftMargin = 0
        params.rightMargin = 0
    }
}

fun calculatePaidOff(monthCount: Int, date: Date): String {
    return DateFormats.addMonthsToDate(monthCount = monthCount, date = date).asFormattedDateWithDot()
}

fun returnValueIfNull(editText: EditText): String{
    return if(editText.text.toString().trim().isNullOrEmpty())
        editText.hint.toString()
    else
        editText.text.toString()
}

fun getFloatValue(s: String): Float {
    return if (s.isNotEmpty()) s.replace(" ", "").replace("$", "")
        .toFloat() else 0.0F
}

fun getValor(s: CharSequence): Double {
    return if (s.isNotEmpty()) s.toString().replace("", " ").replace(" ", "")
        .toDouble() else 0.0
}