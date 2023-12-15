/*
 * Created by Elnur Hajiyev on on 8/9/22, 5:44 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.exchange.currency.notification.entity

import androidx.annotation.StringDef

object DeepLinkTypes {
    const val HOME = "HOME"
    @Retention(AnnotationRetention.SOURCE)
    @StringDef(HOME)
    annotation class DeepLinkTypesDef
}
