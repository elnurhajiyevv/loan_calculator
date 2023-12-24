package loan.calculator.domain.usecase.savepage

import loan.calculator.domain.base.BaseUseCase
import loan.calculator.domain.entity.saved.GetSavedLoanModel
import loan.calculator.domain.exceptions.ErrorConverter
import loan.calculator.domain.repository.SaveRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class InsertSaveLoanUseCase @Inject constructor(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val repository: SaveRepository
) : BaseUseCase<InsertSaveLoanUseCase.Params, Unit>(context, converter) {

    override suspend fun executeOnBackground(params: Params) = repository.saveLoan(params.model)

    class Params(val model: GetSavedLoanModel)

}