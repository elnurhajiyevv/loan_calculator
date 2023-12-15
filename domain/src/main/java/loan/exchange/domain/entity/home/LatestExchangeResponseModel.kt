package loan.exchange.domain.entity.home

import kotlinx.serialization.Serializable

@Serializable
class LatestExchangeResponseModel (
    var result: String? = null,
    var provider: String? = null,
    var documentation: String? = null,
    var terms_of_use: String? = null,
    var time_last_update_unix: Long? = null,
    var time_last_update_utc: String? = null,
    var time_next_update_unix: Long? = null,
    var time_next_update_utc: String? = null,
    var time_eol_unix: Long? = null,
    var base_code: String? = null,
    var rates: Map<String,String>?,
)