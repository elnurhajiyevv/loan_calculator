package loan.calculator.data.errors

import loan.calculator.data.remote.error.ServerProblemDescription
import loan.calculator.domain.exceptions.ErrorMapper
import loan.calculator.domain.exceptions.NetworkError
import loan.calculator.domain.exceptions.ServerError
import loan.calculator.domain.exceptions.UnknownError
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.lang.NullPointerException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class RemoteErrorMapper @Inject constructor() : ErrorMapper {

    override fun mapError(e: Throwable): Throwable = when (e) {
        is HttpException -> mapHttpErrors(e)
        is SocketException,
        is NullPointerException,
        is SocketTimeoutException,
        is UnknownHostException,
        -> NetworkError(e)
        else -> UnknownError(e)
    }

    private fun mapHttpErrors(error: HttpException): Throwable {
        val description = try {
            error
                .response()
                ?.errorBody()
                ?.string()
                ?.let { Json{
                    ignoreUnknownKeys = true
                }.decodeFromString<ServerProblemDescription>(it) }

        } catch (ex: Throwable) {
            ex
            null
        } ?: ServerProblemDescription()

        return when (error.code()) {
            in 400..500 -> {
                ServerError.ClientError(description.error?.code?: 1000, description.error?.message ?: "")
            }
            in 500..600 -> {
                ServerError.ServerIsDown(5000, "Server is down")
            }
            else -> {
                ServerError.Unknown(6000, "Unknown error")
            }
        }
    }
}