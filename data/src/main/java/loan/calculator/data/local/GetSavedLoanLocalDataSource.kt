package loan.calculator.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import loan.calculator.data.mapper.toLocal
import loan.calculator.domain.entity.saved.GetSavedLoanModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetSavedLoanLocalDataSource @Inject constructor(
    private val getSavedLoanDao: GetSavedLoanDao,
    private val ioDispatcher: CoroutineContext,
) {
    fun observeSavedLoans(): Flow<List<GetSavedLoanLocalDto>> {
        return getSavedLoanDao.observeSavedLoan()
    }

    suspend fun getSavedLoan(name: String): GetSavedLoanLocalDto {
        return getSavedLoanDao.getSavedLoan(name = name)
    }

    suspend fun deleteSavedLoan(name: String) {
        return getSavedLoanDao.deleteSavedLoan(name = name)
    }

    suspend fun saveLoans(
        savedLoans: List<GetSavedLoanModel>
    ) {
        return withContext(ioDispatcher) {
            val savedLoans = savedLoans.map { it.toLocal() }
            getSavedLoanDao.insertSavedLoans(savedLoans)
        }
    }

    suspend fun saveLoan(saveLoan: GetSavedLoanModel) {
        return withContext(ioDispatcher) {
            getSavedLoanDao.insertSavedLoan(saveLoan.toLocal())
        }
    }

    suspend fun clearData() {
        withContext(ioDispatcher) {
            getSavedLoanDao.deleteSavedLoanList()
        }
    }
    suspend fun flushAndInsertSavedLoan(responseModel: GetSavedLoanLocalDto) {
        return withContext(ioDispatcher) {
            getSavedLoanDao.flushAndInsertSavedLoan(responseModel)
        }
    }
}