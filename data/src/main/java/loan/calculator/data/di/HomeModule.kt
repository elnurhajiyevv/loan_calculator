package loan.calculator.data.di

import loan.calculator.data.repository.SaveRepositoryImpl
import loan.calculator.domain.repository.SaveRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SaveModule {

    @Binds
    fun bindSaveRepository(saveRepository: SaveRepositoryImpl): SaveRepository
}