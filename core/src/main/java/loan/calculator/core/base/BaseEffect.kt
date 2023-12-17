package loan.calculator.core.base

interface BaseEffect

sealed class BaseUiError : BaseEffect
object NoInternet : BaseUiError()
class BackEndError(val error: MessageError?=null) : BaseUiError()
class UnknownError(val cause: Throwable) : BaseUiError()
object NotAuthorized : BaseUiError()
class MessageError(
    val code: Int,
    val message: String? = null
) : BaseUiError()
