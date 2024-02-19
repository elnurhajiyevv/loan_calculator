package loan.calculator.domain.repository

import kotlinx.coroutines.flow.Flow
import loan.calculator.domain.entity.saved.GetSavedLoanModel
import loan.calculator.domain.entity.unit.IconModel

interface SaveRepository {

    fun observeSavedLoans(): Flow<List<GetSavedLoanModel>>

    fun getSavedLoan(name: String): Flow<GetSavedLoanModel>

    fun deleteSavedLoan(name: String)

    fun saveLoan(model: GetSavedLoanModel)

    fun saveLoans(models: List<GetSavedLoanModel>)

    fun clearData()

    fun getIconModel(): IconModel

    fun setIconModel(iconModel: IconModel)

}