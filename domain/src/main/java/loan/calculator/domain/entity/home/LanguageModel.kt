package loan.calculator.domain.entity.home

import kotlinx.serialization.Serializable

@Serializable
class LanguageModel (
    val code: String,
    val name: String,
    val nationalName: String,
    var isSelected: Boolean = false
)