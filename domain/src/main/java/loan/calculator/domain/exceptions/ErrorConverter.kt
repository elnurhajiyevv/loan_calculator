package loan.calculator.domain.exceptions

fun interface ErrorConverter {
    fun convert(throwable: Throwable): Throwable
}