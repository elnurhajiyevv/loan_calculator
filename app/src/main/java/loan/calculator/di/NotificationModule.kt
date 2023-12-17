package loan.calculator.di

import loan.calculator.notification.NotificationHandler
import loan.calculator.notification.NotificationHandlerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NotificationModule {

    @Binds
    @Singleton
    fun bindNotificationHandler(notificationHandler: NotificationHandlerImpl): NotificationHandler
}