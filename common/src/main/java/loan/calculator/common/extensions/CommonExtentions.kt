package loan.calculator.common.extensions

import android.annotation.SuppressLint
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.reflect.KClass


fun <T : Any> Any.isInstanceOf(comparate: KClass<T>): T? {
    return if (comparate.java.isAssignableFrom(this::class.java)) {
        this as T
    } else {
        null
    }
}


fun Number?.asFormattedAmount(): String {
    val formatter = DecimalFormat("###,###,##0.00")
    return formatter.format(this).replace(',', ' ')
}

fun Number?.withFormattedCurrency(currency: String): String {
    val formatter = DecimalFormat("###,###,##0.00")
    return "$currency ${formatter.format(this).replace(',', ' ')}"
}

fun String.asFormattedAmount(): String {
    val formatter = DecimalFormat("###,###,##0.00")
    return formatter.format(this.toBigDecimal()).replace(',', ' ')
}

fun String.asFormattedDateTime(ofInputPattern: String? = null, ofOutputPattern: String? = null): CharSequence? {
    val input = SimpleDateFormat(ofInputPattern ?: "yyyy-MM-dd'T'HH:mm:ss", Locale.UK)
    val output = SimpleDateFormat(ofOutputPattern ?: "dd MMM yyyy, hh:mm", Locale.UK)
    return try {
        val getAbbreviate = input.parse(this)
        getAbbreviate?.run {
            output.format(this)
        }
    } catch (e: Exception) {
        null
    }
}

fun String.asTimeStamp(ofInputPattern: String? = null, ofOutputPattern: String? = null): Long {
    val input = SimpleDateFormat(ofInputPattern ?: "yyyy-MM-dd'T'HH:mm:ss", Locale.UK)
    return try {
        val getAbbreviate = input.parse(this)
        getAbbreviate?.time ?: 0
    } catch (e: Exception) {
        0
    }
}


fun Any?.isNull(): Boolean {
    return this == null
}

fun Any?.isNotNull(): Boolean {
    return this != null
}

fun <T> withTryCatch(default: T, function: () -> T): T {
    return try {
        function.invoke()
    } catch (e: Exception) {
        default
    }
}

fun Date.asFormattedDate(format: String = "dd MMMM yyyy"): String {
    return try {
        SimpleDateFormat(format, Locale.UK).format(this)
    } catch (e: Exception) {
        ""
    }
}

fun Date.asFormattedTime(): String? {
    return SimpleDateFormat("HH:mm", Locale.UK).format(this)
}

@SuppressLint("SimpleDateFormat")
fun Date.asFormattedDateTime(): String {
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(this)
}
