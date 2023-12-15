package loan.exchange.domain.base

interface BaseValidator<T> {
    fun isValid(input: T): Boolean
}