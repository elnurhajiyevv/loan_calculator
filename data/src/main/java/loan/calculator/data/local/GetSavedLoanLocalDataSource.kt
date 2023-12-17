package loan.calculator.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetSavedLoanLocalDataSource @Inject constructor(
    private val getSavedLoanDao: GetSavedLoanDao,
    private val ioDispatcher: CoroutineContext,
) {
    fun observeSavedLoans(): Flow<GetSavedLoanLocalDto> {
        return getSavedLoanDao.observeSavedLoanResponse()
    }
    suspend fun flushAndInsertSavedLoan(responseModel: GetSavedLoanLocalDto) {
        return withContext(ioDispatcher) {
            getSavedLoanDao.flushAndInsertSavedLoanResponse(responseModel)
        }
    }
}