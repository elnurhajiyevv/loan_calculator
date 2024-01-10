package loan.calculator.uikit.util

import android.view.View
import android.widget.EditText
import android.widget.ImageView
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

fun calculatePaidOff(monthCount: Int, date: Date): String {
    return DateFormats.addMonthsToDate(monthCount = monthCount, date = date).asFormattedDateWithDot()
}