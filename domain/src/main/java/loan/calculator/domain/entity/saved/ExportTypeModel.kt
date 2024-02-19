package loan.calculator.domain.entity.saved

import kotlinx.serialization.Serializable

@Serializable
class ExportTypeModel (
    val name: String,
    val type: Int
)