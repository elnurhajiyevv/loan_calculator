package loan.exchange.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetCurrenciesLocalDataSource @Inject constructor(
    private val getCurrenciesDao: GetCurrenciesDao,
    private val ioDispatcher: CoroutineContext,
) {
    fun observeCurrencies(): Flow<GetCurrenciesLocalDto> {
        return getCurrenciesDao.observeCurrenciesResponse()
    }
    suspend fun flushAndInsertCurrencies(responseModel: GetCurrenciesLocalDto) {
        return withContext(ioDispatcher) {
            getCurrenciesDao.flushAndInsertLatestExchangeResponse(responseModel)
        }
    }
}