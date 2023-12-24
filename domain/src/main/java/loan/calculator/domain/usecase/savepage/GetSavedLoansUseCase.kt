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

class GetSavedLoansUseCase @Inject constructor(
    private val context: CoroutineContext,
    private val converter: ErrorConverter,
    private val repository: SaveRepository,
) : BaseFlowUseCase<Unit, List<GetSavedLoanModel>>(context, converter) {

    override fun createFlow(params: Unit): Flow<List<GetSavedLoanModel>> {
        val contactsFlow = repository.observeSavedLoans() //get savedLoans from db
        return contactsFlow
            .flowOn(context)
            .catch { throw converter.convert(it) }
    }

}