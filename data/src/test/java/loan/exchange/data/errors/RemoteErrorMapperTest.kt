package loan.exchange.data.errors

import loan.exchange.domain.exceptions.NetworkError
import loan.exchange.domain.exceptions.UnknownError
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@RunWith(JUnit4::class)
internal class RemoteErrorMapperTest {
    private val mapper = RemoteErrorMapper()

    @Test
    fun verifyUnknownErrorsMapped() {
        assert(mapper.mapError(IOException("mgs")) is UnknownError)
        assert(mapper.mapError(NullPointerException("mgs")) is UnknownError)
        assert(mapper.mapError(IllegalArgumentException("mgs")) is UnknownError)
    }

    @Test
    fun verifyNoInternetErrorsMapped() {
        assert(mapper.mapError(SocketException("mgs")) is NetworkError)
        assert(mapper.mapError(SocketTimeoutException("mgs")) is NetworkError)
        assert(mapper.mapError(UnknownHostException("mgs")) is NetworkError)
    }

}