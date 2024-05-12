/*
 * Created by Elnur Hajiyev on on 8/9/22, 5:07 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.calculator.app.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import loan.calculator.app.MainActivity
import loan.calculator.uikit.R
import loan.calculator.app.notification.entity.DeepLinkTypes
import loan.calculator.app.notification.entity.NotificationModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationHandlerImpl @Inject constructor(@ApplicationContext val context: Context) :
    NotificationHandler {

    companion object {
        private const val CHANNEL_ID = "default_notification_channel"
    }

    override fun showNotification(notificationModel: NotificationModel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context)
            mBuilder.setSmallIcon(android.R.drawable.checkbox_on_background)
            mBuilder.priority = NotificationCompat.PRIORITY_MAX
            mBuilder.setContentTitle(notificationModel.title)
                .setContentText(notificationModel.message)
                .setAutoCancel(false)
            mBuilder.setContentIntent(pendingIntent(notificationModel))
            val mNotificationManager: NotificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            createNotificationChannel()
            mBuilder.setChannelId(CHANNEL_ID)
            mNotificationManager.notify(System.currentTimeMillis().toInt(), mBuilder.build())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun deleteNotificationChannel(channelId: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.deleteNotificationChannel(channelId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val mChannel = NotificationChannel(CHANNEL_ID, context.getString(R.string.default_channel_name), importance)
        mChannel.lightColor = Color.GREEN
        mChannel.description = context.getString(R.string.app_name)
        mChannel.enableVibration(true)
        mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
    }

    private fun pendingIntent(notificationModel: NotificationModel) =
        NavDeepLinkBuilder(context)
            .setComponentName(MainActivity::class.java)
            .setGraph(loan.calculator.app.R.navigation.main_graph)
            .setDestination(getGraphDestination(notificationModel.deepLink))
            .setArguments(notificationModel.extras)
            .createPendingIntent()


    private fun getGraphDestination(@DeepLinkTypes.DeepLinkTypesDef deepLink: String?): Int {
        return when (deepLink) {
            DeepLinkTypes.HOME -> loan.calculator.loan.R.id.loanPageFragment
            else -> loan.calculator.loan.R.id.loanPageFragment
        }
    }


}