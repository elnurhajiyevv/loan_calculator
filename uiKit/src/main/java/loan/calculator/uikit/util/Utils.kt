package loan.calculator.uikit.util

import android.view.View

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