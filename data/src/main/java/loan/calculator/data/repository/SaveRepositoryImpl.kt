package loan.calculator.data.repository

import loan.calculator.data.local.GetSavedLoanLocalDataSource
import loan.calculator.data.mapper.toLocal
import loan.calculator.domain.repository.SaveRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import loan.calculator.data.mapper.toRemote
import loan.calculator.domain.entity.saved.GetSavedLoanModel
import javax.inject.Inject

class SaveRepositoryImpl @Inject constructor(
    private val getSavedLoanLocalDataSource: GetSavedLoanLocalDataSource,
) : SaveRepository {
    override fun observeSavedLoans(): Flow<List<GetSavedLoanModel>> {
        return getSavedLoanLocalDataSource.observeSavedLoans().map { list->
            list.map { it.toRemote() }
        }
    }

    override suspend fun getSavedLoan(name: String): GetSavedLoanModel {
        return getSavedLoanLocalDataSource.getSavedLoan(name).toRemote()
    }

    override suspend fun deleteSavedLoan(name: String) {
        return getSavedLoanLocalDataSource.deleteSavedLoan(name)
    }

    override suspend fun saveLoan(model: GetSavedLoanModel) {
        return getSavedLoanLocalDataSource.saveLoan(saveLoan = model)
    }

    override suspend fun saveLoans(models: List<GetSavedLoanModel>) {
        return getSavedLoanLocalDataSource.saveLoans(savedLoans = models)
    }


    override suspend fun clearData() {
        getSavedLoanLocalDataSource.clearData()
    }

}