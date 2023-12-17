/*
 * Created by Hajiyev Elnur on on 8/02/22, 5:44 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.calculator.notification.entity

import android.os.Bundle

class NotificationModel(
    val title: String?,
    val message: String?,
    val deepLink: String?,
    var extras: Bundle?
) {

    private constructor(builder: Builder) : this(
        builder.title,
        builder.message,
        builder.deepLink,
        builder.extras
    )

    companion object {
        inline fun notification(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var deepLink: String? = null
        var title: String? = null
        var message: String? = null
        var navClass: Any? = null
        var graph: Int = 0
        var destination: Int = 0
        var extras: Bundle? = null
        fun build() = NotificationModel(this)
    }
}