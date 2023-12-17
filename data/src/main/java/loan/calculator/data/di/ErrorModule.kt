package loan.calculator.data.di

import loan.calculator.data.errors.RemoteErrorMapper
import loan.calculator.domain.exceptions.ErrorMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
interface ErrorModule {

    @Binds
    @IntoSet
    fun bindErrorMapper(remoteErrorMapper: RemoteErrorMapper): ErrorMapper
}