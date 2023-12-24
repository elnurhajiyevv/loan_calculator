package loan.calculator.domain.usecase.savepage

import loan.calculator.domain.base.BaseUseCase
import loan.calculator.domain.exceptions.ErrorConverter
import loan.calculator.domain.repository.SaveRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DeleteSavedLoanUseCase @Inject constructor(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val repository: SaveRepository
) : BaseUseCase<DeleteSavedLoanUseCase.Params, Unit>(context, converter) {

    override suspend fun executeOnBackground(params: Params) = repository.deleteSavedLoan(params.name)

    class Params(val name: String)

}