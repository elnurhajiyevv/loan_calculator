package loan.exchange.domain.di

import loan.exchange.domain.exceptions.ErrorConverter
import loan.exchange.domain.exceptions.ErrorConverterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ErrorConverterModule {
    @Binds
    fun bindErrorConverter(errorConverter: ErrorConverterImpl): ErrorConverter
}