package loan.calculator.data.mapper

import loan.calculator.data.local.GetSavedLoanLocalDto
import loan.calculator.domain.entity.saved.GetSavedLoanModel

fun GetSavedLoanLocalDto.toRemote(
) = GetSavedLoanModel(
    name = name,
    code = code,
    description = description
)