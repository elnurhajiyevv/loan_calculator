package loan.calculator.core.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions

fun Fragment.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun View.showKeyboard(){
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, 0)
}

fun View.hideKeyboard(){
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun NavController.deeplinkNavigate(
    direction: String,
    extras: MutableMap<String, String>? = null,
    isInclusive: Boolean = false
) {
    var deeplinkDirection = direction
    extras?.forEach { (key, value) ->
        deeplinkDirection = deeplinkDirection.replace("{$key}", value)
    }
    val deepLink = NavDeepLinkRequest.Builder
        .fromUri(deeplinkDirection.toUri())
        .build()

    val currentId = currentDestination?.id
    if (currentId != null && isInclusive) {
        val options = NavOptions.Builder()
            .setPopUpTo(currentId, true)
            .build()
        navigate(deepLink, options)
    } else
        navigate(deepLink)
}

object DeeplinkNavigationTypes {
    private const val DOMAIN = "loan://"
    const val AMORTIZATION = "${DOMAIN}amortization/{${NavigationArgs.LOAN}}"
    const val SAVED_AMORTIZATION = "${DOMAIN}savedamortization/{${NavigationArgs.LOAN}}"
    const val INIT_PAGE = "${DOMAIN}init"
    const val HOME_PAGE = "${DOMAIN}home"
    const val INTRO_PAGE = "${DOMAIN}intro"
    const val SIGN_UP_PAGE = "${DOMAIN}signup"
    const val LOCATION_PAGE = "${DOMAIN}location"
    const val RESET_PAGE = "${DOMAIN}reset"
    const val LOGIN_PAGE = "${DOMAIN}login"
    const val EMAIL_FORGOT_PAGE = "${DOMAIN}emailforgot"
    const val VERIFY_PAGE = "${DOMAIN}verify"
}

object NavigationArgs {
    const val LOAN = "loanInfo"
}