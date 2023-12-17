package loan.calculator.domain.base

interface BaseValidator<T> {
    fun isValid(input: T): Boolean
}