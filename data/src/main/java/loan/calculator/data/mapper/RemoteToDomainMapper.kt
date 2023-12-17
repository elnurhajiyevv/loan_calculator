package loan.calculator.data.mapper

import loan.calculator.data.local.GetSavedLoanLocalDto
import loan.calculator.domain.entity.home.response.GetSavedLoanResponseModel

fun GetSavedLoanResponseModel.toLocal(
) = GetSavedLoanLocalDto(
    name = name,
    description = description
)

