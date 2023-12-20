package loan.calculator.domain.entity.home

import kotlinx.serialization.Serializable

@Serializable
class LanguageModel (
    var name: String,
    var nationalName: String
)