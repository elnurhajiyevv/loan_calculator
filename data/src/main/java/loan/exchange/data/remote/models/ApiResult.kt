package loan.exchange.data.remote.models

import androidx.annotation.Keep

@Keep
class ApiResult<T>(
    val data: T
)