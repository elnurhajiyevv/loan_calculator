package loan.calculator.data.remote.home

import androidx.annotation.Keep

@Keep
data class CountryModelDto(
    val id: Int?,
    val name: String?,
    val ip: String?,
    val status: String?,
    val iconPath: String?
)