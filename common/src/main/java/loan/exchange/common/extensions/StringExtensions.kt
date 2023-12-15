package loan.exchange.common.extensions

import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import loan.exchange.common.R
import java.text.NumberFormat
import java.util.*

/**
 * check card data with Luhn method to see if it is a valid card
 * @return boolean as result of card check
 */
fun String.checkCardWithLuhn(): Boolean {
    return try {
        if (isNotEmpty()) {
            var sum = 0
            var alternate = false
            for (i in length - 1 downTo 0) {
                var n = Integer.parseInt(substring(i, i + 1))
                if (alternate) {
                    n *= 2
                    if (n > 9) {
                        n = n % 10 + 1
                    }
                }
                sum += n
                alternate = !alternate
            }
            sum % 10 == 0
        } else
            false
    } catch (e: Exception) {
        false
    }
}

/**
 * Replaces all non digits from string
 */
fun String.replaceNonDigits(): String {
    return replace("[^\\d.]".toRegex(), "")
}


fun String.getCountryFlagDrawableName() = "country_flag_$this"

fun String?.getNullIfEmpty(): String? {
    return if (isNullOrEmpty()) null else this
}

fun String?.getEmptyIfNull(): String {
    return if (isNullOrEmpty()) "" else this
}


fun String?.asSpannedHtml(): Spanned {
    return when {
        this == null -> {
            SpannableString("")
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
            Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
        }
        else -> {
            Html.fromHtml(this)
        }
    }
}

fun String.formatToAmount(): String {
    val locale: Locale = Locale.UK
    val currency = Currency.getInstance(locale)
    val cleanString = this.replace("[${currency.symbol},.]".toRegex(), "")
    val parsed = cleanString.toDouble()
    return NumberFormat.getCurrencyInstance(locale).format(parsed / 100)
        .replace("[${currency.symbol},]".toRegex(), "")

}
