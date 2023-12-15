package loan.exchange.domain.validators

import loan.exchange.domain.base.BaseValidator
import java.util.regex.Pattern
import javax.inject.Inject


class EmailValidator @Inject constructor() : BaseValidator<String> {

    private val EMAIL_ADDRESS: Pattern = Pattern.compile(
        "[a-zA-Z0-9+._%\\-]{1,256}" +
                "@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    override fun isValid(input: String): Boolean {
        return EMAIL_ADDRESS.matcher(input).matches()
    }

}