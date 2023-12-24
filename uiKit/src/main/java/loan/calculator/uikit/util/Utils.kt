package loan.calculator.uikit.util

import android.view.View
import android.widget.ImageView

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