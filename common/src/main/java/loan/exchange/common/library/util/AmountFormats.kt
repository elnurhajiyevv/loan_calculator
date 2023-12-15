package loan.exchange.common.library.util

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

fun convertToAmountView(amount: String): String {
    val df = DecimalFormat("# ##0.00", DecimalFormatSymbols(Locale("en", "EN")))
    val value = BigDecimal(amount)
    return df.format(value.toFloat())
}