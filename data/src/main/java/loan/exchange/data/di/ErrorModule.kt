package loan.exchange.data.di

import loan.exchange.data.errors.RemoteErrorMapper
import loan.exchange.domain.exceptions.ErrorMapper
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