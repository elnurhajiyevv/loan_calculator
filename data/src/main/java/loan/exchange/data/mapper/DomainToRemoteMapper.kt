package loan.exchange.data.mapper

import loan.exchange.data.local.GetCurrenciesLocalDto
import loan.exchange.domain.entity.home.response.GetCurrenciesResponseModel

fun GetCurrenciesLocalDto.toRemote(
) = GetCurrenciesResponseModel(
    success = success,
    result = result?.let { fromString(it) }
)