/*
 * Created by Elnur Hajiyev on on 7/27/22, 10:24 AM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.calculator.data.entities

class RetrofitHostConfig(
    val baseUrl: String,
    val serverPort: String,
    val readTimeout: Long,
    val connectTimeout: Long,
    val mainSHSKey: String,
    val backupSHAKey: String
)