package loan.exchange.data.repository

import android.content.Context
import loan.exchange.common.library.prefs.sharedprefs.MainSharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AuthPreferences @Inject constructor(@ApplicationContext context: Context) : MainSharedPreferences(context) {
    override val filename = "auth_prefs"

    companion object {
        const val LAST_SELECTED_AMOUNT = "last_selected_amount"
        const val LAST_SELECTED_CONVERTER = "last_selected_converter"
        const val LAST_RATE = "last_rate"
    }
    var lastSelectedAmount
        get() = get(LAST_SELECTED_AMOUNT, "USD")
        set(value) = set(LAST_SELECTED_AMOUNT, value)

    var lastSelectedConverter
        get() = get(LAST_SELECTED_CONVERTER, "EUR")
        set(value) = set(LAST_SELECTED_CONVERTER, value)

    var lastRate
        get() = get(LAST_RATE, 0.918345f)
        set(value) = set(LAST_RATE, value)

}
