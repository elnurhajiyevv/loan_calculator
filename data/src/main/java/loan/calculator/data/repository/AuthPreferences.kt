package loan.calculator.data.repository

import android.content.Context
import loan.calculator.common.library.prefs.sharedprefs.MainSharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AuthPreferences @Inject constructor(@ApplicationContext context: Context) : MainSharedPreferences(context) {
    override val filename = "auth_prefs"

    companion object {
        const val LIGHT_THEME = "LIGHT_THEME"
    }
    var isLightTheme
        get() = get(LIGHT_THEME, false)
        set(value) = set(LIGHT_THEME, value)
}
