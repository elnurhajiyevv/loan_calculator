package loan.calculator.common.library.util

import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

object DateFormats {
    const val DATE_FORMAT_YYYY_MM_DD = "YYYY-MM-dd"

    fun addDaysToDate(dayCount: Int, date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_YEAR, dayCount)
        return calendar.time
    }

    fun Date.convertDateToCalendar(): Calendar {
        val cal = Calendar.getInstance()
        cal.time = this
        return cal
    }

    fun Long.millisToDay(): Long {
        return TimeUnit.MILLISECONDS.toDays(this)
    }
}