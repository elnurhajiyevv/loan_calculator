package loan.calculator.domain.repository

import kotlinx.coroutines.flow.Flow
import loan.calculator.domain.entity.home.response.GetSavedLoanResponseModel

interface SaveRepository {

    suspend fun refreshSavedLoan()
    fun observeSavedLoan(): Flow<GetSavedLoanResponseModel>

}