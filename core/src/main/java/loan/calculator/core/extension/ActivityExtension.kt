package loan.calculator.core.extension

import android.app.Activity

/**
 * Created by elnurh on 4/30/2024 for GoldenPay.
 */
fun Activity.setWindowFlag(bits: Int, on: Boolean) {
    val win = this.window
    val winParams = win.attributes
    if (on) {
        winParams.flags = winParams.flags or bits
    } else {
        winParams.flags = winParams.flags and bits.inv()
    }
    win.attributes = winParams
}