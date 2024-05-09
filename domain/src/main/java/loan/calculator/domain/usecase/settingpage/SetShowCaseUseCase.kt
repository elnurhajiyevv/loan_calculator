package loan.calculator.domain.usecase.settingpage

import loan.calculator.domain.base.BaseSingleUseCase
import loan.calculator.domain.exceptions.ErrorConverter
import loan.calculator.domain.repository.SettingRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SetShowCaseUseCase @Inject constructor(
    private val repository: SettingRepository,
    context: CoroutineContext,
    converter: ErrorConverter,
) : BaseSingleUseCase<SetShowCaseUseCase.Params, Unit>(context, converter) {

    override fun invoke(params: Params) {
        return repository.setShowCase(params.value)
    }

    data class Params(val value: Boolean)

}