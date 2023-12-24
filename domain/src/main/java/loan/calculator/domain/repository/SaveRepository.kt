package loan.calculator.domain.repository

import kotlinx.coroutines.flow.Flow
import loan.calculator.domain.entity.saved.GetSavedLoanModel

interface SaveRepository {

    fun observeSavedLoans(): Flow<List<GetSavedLoanModel>>

    suspend fun getSavedLoan(name: String): GetSavedLoanModel

    suspend fun deleteSavedLoan(name: String)

    suspend fun saveLoan(model: GetSavedLoanModel)

    suspend fun saveLoans(models: List<GetSavedLoanModel>)

    suspend fun clearData()

}