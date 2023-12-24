package loan.calculator.domain.usecase.settingpage

import loan.calculator.domain.base.BaseSingleUseCase
import loan.calculator.domain.entity.home.LanguageModel
import loan.calculator.domain.exceptions.ErrorConverter
import loan.calculator.domain.repository.SettingRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetLanguageUseCase @Inject constructor(
    private val repository: SettingRepository,
    context: CoroutineContext,
    converter: ErrorConverter,
) : BaseSingleUseCase<Unit, LanguageModel>(context, converter) {

    override fun invoke(params: Unit): LanguageModel {
        return repository.getLanguage()
    }
}