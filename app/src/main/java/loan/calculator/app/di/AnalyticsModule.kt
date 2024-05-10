package loan.calculator.app.di

import loan.calculator.app.FirebaseAnalyticsRepository
import loan.calculator.domain.repository.AnalyticsRepository
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
