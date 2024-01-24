package loan.calculator.domain.usecase.savepage

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import loan.calculator.domain.base.BaseFlowUseCase
import loan.calculator.domain.entity.saved.GetSavedLoanModel
import loan.calculator.domain.exceptions.ErrorConverter
import loan.calculator.domain.repository.SaveRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetSavedLoanUseCase @Inject constructor(
    val context: CoroutineContext,
    val converter: ErrorConverter,
    private val repository: SaveRepository
) : BaseFlowUseCase<GetSavedLoanUseCase.Params, GetSavedLoanModel?>(context, converter) {

    override fun createFlow(params: Params): Flow<GetSavedLoanModel> {
        val contactsFlow = repository.getSavedLoan(params.name)
        return contactsFlow
            .flowOn(context)
            .catch { throw converter.convert(it) }
    }
    class Params(val name: String)

}