package loan.calculator.domain.usecase.settingpage

import loan.calculator.domain.base.BaseUseCase
import loan.calculator.domain.exceptions.ErrorConverter
import loan.calculator.domain.repository.DeviceConfigRepository
import loan.calculator.domain.repository.SettingRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetAppColorUseCase @Inject constructor(
    context: CoroutineContext,
    converter: ErrorConverter,
    val repository: SettingRepository
) : BaseUseCase<Unit, Int>(context, converter) {

    override suspend fun executeOnBackground(params: Unit): Int {
        return repository.getColor()
    }

}