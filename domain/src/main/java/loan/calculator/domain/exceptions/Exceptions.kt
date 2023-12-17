package loan.calculator.domain.exceptions

import java.io.IOException

abstract class HandledException(
    cause: Throwable? = null,
    messages: String? = null
) : IOException(messages, cause)

class UnknownError(cause: Throwable?) : HandledException(cause)

class NetworkError(cause: Throwable?) : HandledException(cause)

sealed class ServerError(
    open val code: Int,
    open val serverMessage: String
) : HandledException() {

    data class ClientError(
        override val code: Int,
        override val message: String
    ) : ServerError(code, message)

    data class ServerIsDown(
        override val code: Int,
        override val message: String
    ) : ServerError(code, message)

    data class Unknown(
        override val code: Int,
        override val message: String
    ) : ServerError(code, message)

    data class NotAuthorized(
        override val code: Int,
        override val message: String
    ) : ServerError(code, message)
}

