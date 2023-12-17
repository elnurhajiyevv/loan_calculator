package loan.calculator.domain.entity.home

import kotlinx.serialization.Serializable

@Serializable
class SavedModel (
    var name:String,
    var description: String?
)