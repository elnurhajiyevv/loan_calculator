package loan.exchange.data.mapper

import loan.exchange.data.local.GetCurrenciesLocalDto
import loan.exchange.domain.entity.home.response.GetCurrenciesResponseModel

fun GetCurrenciesResponseModel.toLocal(
) = GetCurrenciesLocalDto(
    success = success,
    result = result?.let { fromStringMap(it) }
)

