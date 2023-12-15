package loan.exchange.data.repository

import loan.exchange.data.local.GetCurrenciesLocalDataSource
import loan.exchange.data.mapper.toLocal
import loan.exchange.data.remote.api.HomeApi
import loan.exchange.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import loan.exchange.data.mapper.toRemote
import loan.exchange.domain.entity.home.response.ConvertCurrencyResponseModel
import loan.exchange.domain.entity.home.response.GetCurrenciesResponseModel
import loan.exchange.domain.entity.home.response.LatestRatesResponseModel
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeApi: HomeApi,
    private val authPreferences: AuthPreferences,
    private val getCurrenciesLocalDataSource: GetCurrenciesLocalDataSource,
) : HomeRepository {
    override suspend fun refreshCurrencies() {
        homeApi.getCurrencies().apply {
            getCurrenciesLocalDataSource.flushAndInsertCurrencies(this.toLocal())
        }
    }

    override fun observeCurrencies(): Flow<GetCurrenciesResponseModel> {
        return getCurrenciesLocalDataSource.observeCurrencies().map {
            it.toRemote()
        }
    }

    override suspend fun getLatestExchangeRates(
        base: String,
        currencies: String?
    ): LatestRatesResponseModel {
        return homeApi.getLatestExchangeRates(base = base, currencies = currencies)
    }

    override suspend fun getConvertedCurrency(
        from: String,
        to: String,
        amount: String
    ): ConvertCurrencyResponseModel {
        return homeApi.getConvertedCurrency(from = from, to = to, amount = amount)
    }

    override fun getLastSelectedAmount() = authPreferences.lastSelectedAmount

    override fun setLastSelectedAmount(lastSelectedAmount: String) {
        authPreferences.lastSelectedAmount = lastSelectedAmount
    }

    override fun getLastSelectedConverter() = authPreferences.lastSelectedConverter

    override fun setLastSelectedConverter(lastSelectedConverter: String) {
        authPreferences.lastSelectedConverter = lastSelectedConverter
    }

    override fun getLastRate() = authPreferences.lastRate

    override fun setLastRate(rate: Float) {
        authPreferences.lastRate = rate
    }

}