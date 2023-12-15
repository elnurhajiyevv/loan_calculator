package loan.exchange.data.di

import loan.exchange.data.repository.HomeRepositoryImpl
import loan.exchange.domain.repository.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface HomeModule {

    @Binds
    fun bindHomeRepository(homeRepository: HomeRepositoryImpl): HomeRepository
}