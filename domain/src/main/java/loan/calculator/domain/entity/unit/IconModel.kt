package loan.calculator.domain.entity.unit

import kotlinx.serialization.Serializable
import loan.calculator.domain.entity.enum.SELECT_TYPE_LOAN

@Serializable
class IconModel (
    var iconResource: SELECT_TYPE_LOAN = SELECT_TYPE_LOAN.HOME,
    var backgroundColor: Int,
    var isSelected: Boolean = false
)