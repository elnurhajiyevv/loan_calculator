package loan.calculator.common.extensions

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat


fun Context.fromStringToDrawable(resourceName: String): Int {
    return resources.getIdentifier(resourceName, "drawable", packageName)
}

fun Context.vibratePhone(pattern: LongArray = longArrayOf(0, 250, 500, 250, 500)) {
    val vibrator = ContextCompat.getSystemService(this, Vibrator::class.java)
    vibrator?.let {
        if (!vibrator.hasVibrator())
            return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(pattern, -1)
        }
    }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}