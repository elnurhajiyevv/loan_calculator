package loan.calculator.domain.validators

import loan.calculator.domain.base.BaseValidator
import javax.inject.Inject


class PassCodeValidator @Inject constructor() : BaseValidator<String> {
    private val validatorRegex by lazy { "^\\b([0-9]+)\\b(?<!1111|2222|3333|4444|5555|6666|7777|8888|9999|0000|1234|2580|0852|1212)".toRegex() }

    override fun isValid(input: String): Boolean {
        return validatorRegex.matches(input)
    }

}