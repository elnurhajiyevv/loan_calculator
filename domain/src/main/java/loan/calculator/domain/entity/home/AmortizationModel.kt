package loan.calculator.domain.entity.home

import kotlinx.serialization.Serializable

@Serializable
class AmortizationModel (
    var month:Int,
    var beginningBalance: Double?,
    var interest: Double?,
    var principal: Double?,
    var endingBalance: Double?,
    val type: Int? = 0,
    val countOfYear: Int? = 0,
    var numberOfItems: Int = 0
): java.io.Serializable