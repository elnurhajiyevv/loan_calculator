package loan.calculator.data.remote.interceptor

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class AuthInterceptor @Inject constructor(
    @ApplicationContext private val context: Context,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.run {
            proceed(
                request()
                    .newBuilder()
                    .addHeader("X-RapidAPI-Key", "12f455edf5msh8051ad1f9f01053p1b5255jsn268ccd59184f")
                    .addHeader("X-RapidAPI-Host", "currency-converter-pro1.p.rapidapi.com")
                    .build()
            )
        }
}
