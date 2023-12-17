package loan.calculator.domain.usecase.savepage

import kotlinx.coroutines.flow.Flow
import loan.calculator.domain.base.BaseFlowUseCase
import loan.calculator.domain.base.BaseUseCase
import loan.calculator.domain.entity.home.response.GetSavedLoanResponseModel
import loan.calculator.domain.exceptions.ErrorConverter
import loan.calculator.domain.repository.SaveRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RefreshSavedLoanUseCase @Inject constructor(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val repository: SaveRepository
) : BaseUseCase<RefreshSavedLoanUseCase.Param, Unit>(context, converter) {

    class Param()
    override suspend fun executeOnBackground(params: Param) {
        repository.refreshSavedLoan()
    }
}

class ObserveSavedLoanUseCase @Inject constructor(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val repository: SaveRepository,
) : BaseFlowUseCase<ObserveSavedLoanUseCase.Param, GetSavedLoanResponseModel>(context, converter) {

    class Param
    override fun createFlow(params: Param): Flow<GetSavedLoanResponseModel> {
        return repository.observeSavedLoan()
    }
}