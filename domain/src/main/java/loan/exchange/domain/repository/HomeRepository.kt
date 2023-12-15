package loan.exchange.domain.repository

import kotlinx.coroutines.flow.Flow
import loan.exchange.domain.entity.home.response.ConvertCurrencyResponseModel
import loan.exchange.domain.entity.home.response.GetCurrenciesResponseModel
import loan.exchange.domain.entity.home.response.LatestRatesResponseModel

interface HomeRepository {
    suspend fun refreshCurrencies()
    fun observeCurrencies(): Flow<GetCurrenciesResponseModel>

    suspend fun getLatestExchangeRates(base: String, currencies: String?) : LatestRatesResponseModel

    suspend fun getConvertedCurrency(from: String, to: String, amount: String) : ConvertCurrencyResponseModel

    fun getLastSelectedAmount(): String

    fun setLastSelectedAmount(lastSelectedAmount: String)

    fun getLastSelectedConverter(): String

    fun setLastSelectedConverter(lastSelectedConverter: String)

    fun getLastRate(): Float

    fun setLastRate(rate: Float)

}