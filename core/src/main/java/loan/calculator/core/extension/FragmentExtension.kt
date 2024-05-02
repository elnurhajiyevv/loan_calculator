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

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, 0)
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun NavController.deeplinkNavigate(
    direction: String, extras: MutableMap<String, String>? = null, isInclusive: Boolean = false
) {
    var deeplinkDirection = direction
    extras?.forEach { (key, value) ->
        deeplinkDirection = deeplinkDirection.replace("{$key}", value)
    }
    val deepLink = NavDeepLinkRequest.Builder.fromUri(deeplinkDirection.toUri()).build()

    val currentId = currentDestination?.id
    if (currentId != null && isInclusive) {
        val options = NavOptions.Builder().setPopUpTo(currentId, true).build()
        navigate(deepLink, options)
    } else navigate(deepLink)
}

object DeeplinkNavigationTypes {
    private const val DOMAIN = "loan://"
    const val AMORTIZATION =
        "${DOMAIN}amortization/" +
                "{${NavigationArgs.NAME}}/" +
                "{${NavigationArgs.START_DATE}}/" +
                "{${NavigationArgs.PAID_OFF}}/" +
                "{${NavigationArgs.LOAN_AMOUNT}}/" +
                "{${NavigationArgs.INTEREST_RATE}}/" +
                "{${NavigationArgs.FREQUENCY}}/" +
                "{${NavigationArgs.TOTAL_REPAYMENT}}/" +
                "{${NavigationArgs.TERM_IN_MONTH}}/" +
                "{${NavigationArgs.TYPE}}"
}

object NavigationArgs {
    const val NAME = "name"
    const val START_DATE = "startDate"
    const val PAID_OFF = "paidOff"
    const val LOAN_AMOUNT = "loanAmount"
    const val INTEREST_RATE = "interestRate"
    const val FREQUENCY = "frequency"
    const val TOTAL_REPAYMENT = "totalRepayment"
    const val TERM_IN_MONTH = "termInMonth"
    const val TYPE = "type"
}