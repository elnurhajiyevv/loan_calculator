/*
 * Created by Elnur Hajiyev on on 8/6/22, 4:16 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.calculator.app

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import loan.calculator.app.notification.NotificationHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@AndroidEntryPoint
class FirebaseMessageService : FirebaseMessagingService() {


    @Inject
    lateinit var notificationHandler: NotificationHandler

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (!remoteMessage.data.isNullOrEmpty()) {
            runBlocking {
               // notificationHandler.showNotification(remoteMessage.data.fromNotificationToDomain())
            }
        }
    }

    override fun onNewToken(token: String) {

    }

}