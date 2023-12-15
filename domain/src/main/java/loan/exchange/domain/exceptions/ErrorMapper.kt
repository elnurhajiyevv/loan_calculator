package loan.exchange.domain.exceptions

fun interface ErrorMapper {
    fun mapError(e: Throwable): Throwable
}