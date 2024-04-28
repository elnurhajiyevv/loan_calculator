package loan.calculator.domain.entity.home

import kotlinx.serialization.Serializable

@Serializable
class AmortizationModel (
    var month:Int,
    var beginningBalance: Double?,
    var interest: Double?,
    var principal: Double?,
    var endingBalance: Double?,
): java.io.Serializable