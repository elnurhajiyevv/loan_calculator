package loan.calculator.data.repository

import loan.calculator.data.local.GetSavedLoanLocalDataSource
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

    override fun getSavedLoan(name: String): Flow<GetSavedLoanModel> {
        return getSavedLoanLocalDataSource.getSavedLoan(name).map { list->
            list.toRemote()
        }
    }

    override fun deleteSavedLoan(name: String) {
        return getSavedLoanLocalDataSource.deleteSavedLoan(name)
    }

    override fun saveLoan(model: GetSavedLoanModel) {
        return getSavedLoanLocalDataSource.saveLoan(saveLoan = model)
    }

    override fun saveLoans(models: List<GetSavedLoanModel>) {
        return getSavedLoanLocalDataSource.saveLoans(savedLoans = models)
    }


    override fun clearData() {
        getSavedLoanLocalDataSource.clearData()
    }

}