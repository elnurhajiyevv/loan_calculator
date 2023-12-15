package loan.exchange.currency.di

import loan.exchange.currency.notification.NotificationHandler
import loan.exchange.currency.notification.NotificationHandlerImpl
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