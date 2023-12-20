package loan.calculator.domain.usecase.settingpage

import loan.calculator.domain.base.BaseUseCase
import loan.calculator.domain.exceptions.ErrorConverter
import loan.calculator.domain.repository.DeviceConfigRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetAppVersionUseCase @Inject constructor(
    context: CoroutineContext,
    converter: ErrorConverter,
    val repository: DeviceConfigRepository
) : BaseUseCase<Unit, String>(context, converter) {

    override suspend fun executeOnBackground(params: Unit): String {
        return repository.getAppVersion()
    }

}