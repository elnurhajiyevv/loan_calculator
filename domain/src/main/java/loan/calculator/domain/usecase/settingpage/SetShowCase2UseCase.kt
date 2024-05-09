package loan.calculator.domain.usecase.settingpage

import loan.calculator.domain.base.BaseSingleUseCase
import loan.calculator.domain.exceptions.ErrorConverter
import loan.calculator.domain.repository.SettingRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SetShowCase2UseCase @Inject constructor(
    private val repository: SettingRepository,
    context: CoroutineContext,
    converter: ErrorConverter,
) : BaseSingleUseCase<SetShowCase2UseCase.Params, Unit>(context, converter) {

    override fun invoke(params: Params) {
        return repository.setShowCase2(params.value)
    }

    data class Params(val value: Boolean)

}