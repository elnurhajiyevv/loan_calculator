package loan.calculator.domain.exceptions

fun interface ErrorMapper {
    fun mapError(e: Throwable): Throwable
}