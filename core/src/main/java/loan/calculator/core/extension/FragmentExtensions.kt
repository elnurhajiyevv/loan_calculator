/*
 * Created by Elnur Hajiyev on on 7/16/22, 4:24 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.calculator.core.extension

import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest

fun NavController.deeplinkNavigate(direction: String, extras: MutableMap<String, String>? = null) {
    var deeplinkDirection = direction
    extras?.forEach { (key, value) ->
        deeplinkDirection = deeplinkDirection.replace("{$key}", value)
    }
    val deepLink = NavDeepLinkRequest.Builder
        .fromUri(deeplinkDirection.toUri())
        .build()
    navigate(deepLink)
}
