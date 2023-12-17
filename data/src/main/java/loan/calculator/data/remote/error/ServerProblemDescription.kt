package loan.calculator.data.remote.error

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
class ServerProblemDescription(val error: Error? = null)

@Keep
@Serializable
class Error(
    val code: Int,
    val message: String
)