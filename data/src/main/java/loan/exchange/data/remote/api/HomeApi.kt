package loan.exchange.data.remote.api

import loan.exchange.domain.entity.home.response.ConvertCurrencyResponseModel
import loan.exchange.domain.entity.home.response.GetCurrenciesResponseModel
import loan.exchange.domain.entity.home.response.LatestRatesResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {

    @GET("currencies")
    suspend fun getCurrencies(): GetCurrenciesResponseModel

    @GET("latest-rates")
    suspend fun getLatestExchangeRates(
        @Query("base") base: String, @Query("currencies") currencies: String? = null
    ): LatestRatesResponseModel

    @GET("convert")
    suspend fun getConvertedCurrency(
        @Query("from") from: String, @Query("to") to: String, @Query("amount") amount: String,
    ): ConvertCurrencyResponseModel

}