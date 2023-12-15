package loan.exchange.currency.di

import loan.exchange.currency.FirebaseAnalyticsRepository
import loan.exchange.domain.repository.AnalyticsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyticsModule {

    @Binds
    @IntoSet
    abstract fun bindFirebaseAnalyticsRepository(impl: FirebaseAnalyticsRepository): AnalyticsRepository
}
