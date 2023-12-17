/*
 * Created by Elnur Hajiyev on on 8/02/22, 5:07 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.calculator.notification

import loan.calculator.notification.entity.NotificationModel

interface NotificationHandler {
    fun showNotification(notificationModel: NotificationModel)
    fun deleteNotificationChannel(channelId: String)
}