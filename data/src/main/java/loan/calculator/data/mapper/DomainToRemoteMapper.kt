package loan.calculator.data.mapper

import loan.calculator.data.local.GetSavedLoanLocalDto
import loan.calculator.domain.entity.home.response.GetSavedLoanResponseModel

fun GetSavedLoanLocalDto.toRemote(
) = GetSavedLoanResponseModel(
    name = name,
    description = description
)