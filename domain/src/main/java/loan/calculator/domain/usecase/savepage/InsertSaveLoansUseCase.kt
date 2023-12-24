package loan.calculator.domain.usecase.savepage

import loan.calculator.domain.base.BaseUseCase
import loan.calculator.domain.entity.saved.GetSavedLoanModel
import loan.calculator.domain.exceptions.ErrorConverter
import loan.calculator.domain.repository.SaveRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class InsertSaveLoansUseCase @Inject constructor(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val repository: SaveRepository
) : BaseUseCase<InsertSaveLoansUseCase.Params, Unit>(context, converter) {

    override suspend fun executeOnBackground(params: Params) = repository.saveLoans(params.model)

    class Params(val model: List<GetSavedLoanModel>)

}