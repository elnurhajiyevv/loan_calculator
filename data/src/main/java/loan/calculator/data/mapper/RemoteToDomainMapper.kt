package loan.calculator.data.mapper

import loan.calculator.data.local.GetSavedLoanLocalDto
import loan.calculator.domain.entity.saved.GetSavedLoanModel

fun GetSavedLoanModel.toLocal(
) = GetSavedLoanLocalDto(
    name = name,
    code = code,
    description = description,
    type = type,
    background = background,
    src = src,
    startDate = startDate,
    paidOff = paidOff,
    loanAmount = loanAmount,
    interestRate = interestRate,
    compoundingFrequency = compoundingFrequency,
    totalPayment = totalPayment,
    termInMonth = termInMonth
)

