package loan.exchange.domain.exceptions

import javax.inject.Inject

class ErrorConverterImpl @Inject constructor(private val mappers: Set<@JvmSuppressWildcards ErrorMapper>) :
    ErrorConverter {
    override fun convert(throwable: Throwable): Throwable {
        mappers.forEach {
            val error = it.mapError(throwable)
            if (error is HandledException) return error
        }
        return throwable
    }
}