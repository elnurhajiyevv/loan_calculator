package loan.calculator.data.repository

import loan.calculator.data.local.GetSavedLoanLocalDataSource
import loan.calculator.data.mapper.toLocal
import loan.calculator.data.remote.api.HomeApi
import loan.calculator.domain.repository.SaveRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import loan.calculator.data.mapper.toRemote
import loan.calculator.domain.entity.home.response.GetSavedLoanResponseModel
import javax.inject.Inject

class SaveRepositoryImpl @Inject constructor(
    private val homeApi: HomeApi,
    private val authPreferences: AuthPreferences,
    private val getSavedLoanLocalDataSource: GetSavedLoanLocalDataSource,
) : SaveRepository {

    override suspend fun refreshSavedLoan() {
        homeApi.getCurrencies().apply {
            getSavedLoanLocalDataSource.flushAndInsertSavedLoan(this.toLocal())
        }
    }

    override fun observeSavedLoan(): Flow<GetSavedLoanResponseModel> {
        return getSavedLoanLocalDataSource.observeSavedLoans().map {
            it.toRemote()
        }
    }
}