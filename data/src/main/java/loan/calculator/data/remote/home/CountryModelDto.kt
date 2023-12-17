package loan.calculator.data.remote.home

import androidx.annotation.Keep

/**
 * Created by Elnur Hajiyev : elnur.hajiyev@guavapay.com on 6:10 PM, on 9/22/2022.
 * Copyright (c) 2022. All rights reserved to GUAVAPAY
 * This code is copyrighted and using this code without agreement from authors if forbidden.
 */
@Keep
data class CountryModelDto(
    val id: Int?,
    val name: String?,
    val ip: String?,
    val status: String?,
    val iconPath: String?
)