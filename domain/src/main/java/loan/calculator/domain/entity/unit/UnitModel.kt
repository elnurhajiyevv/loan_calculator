package loan.calculator.domain.entity.unit

import kotlinx.serialization.Serializable
import loan.calculator.domain.entity.enums.UNIT_TYPE

@Serializable
class UnitModel (
    var icon:String,
    var text:String,
    var type: UNIT_TYPE
)