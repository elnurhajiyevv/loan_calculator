package loan.calculator.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import loan.calculator.data.mapper.toLocal
import loan.calculator.domain.entity.saved.GetSavedLoanModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetSavedLoanLocalDataSource @Inject constructor(
    private val getSavedLoanDao: GetSavedLoanDao
) {
    fun observeSavedLoans(): Flow<List<GetSavedLoanLocalDto>> {
        return getSavedLoanDao.observeSavedLoan()
    }

    fun getSavedLoan(name: String): Flow<GetSavedLoanLocalDto> {
        return getSavedLoanDao.getSavedLoan(name = name)
    }

    fun deleteSavedLoan(name: String) {
        return getSavedLoanDao.deleteSavedLoan(name = name)
    }

    fun saveLoans(
        savedLoans: List<GetSavedLoanModel>
    ) {
        val savedLoans = savedLoans.map { it.toLocal() }
        getSavedLoanDao.insertSavedLoans(savedLoans)
    }

    fun saveLoan(saveLoan: GetSavedLoanModel) {
        getSavedLoanDao.insertSavedLoan(saveLoan.toLocal())
    }

    fun clearData() {
        getSavedLoanDao.deleteSavedLoanList()
    }
    fun flushAndInsertSavedLoan(responseModel: GetSavedLoanLocalDto) {
        getSavedLoanDao.flushAndInsertSavedLoan(responseModel)
    }
}