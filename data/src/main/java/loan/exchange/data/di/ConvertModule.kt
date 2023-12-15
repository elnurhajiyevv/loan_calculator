package loan.exchange.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import loan.exchange.data.repository.ConvertRepositoryImpl
import loan.exchange.domain.repository.ConvertRepository

@Module
@InstallIn(SingletonComponent::class)
interface ConvertModule {

    @Binds
    fun bindHomeRepository(convertRepository: ConvertRepositoryImpl): ConvertRepository
}