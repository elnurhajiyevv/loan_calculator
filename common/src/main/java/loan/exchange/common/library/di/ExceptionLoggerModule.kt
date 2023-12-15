package loan.exchange.common.library.di


import loan.exchange.common.library.exceptionhandler.ExceptionLogger
import loan.exchange.common.library.exceptionhandler.ExceptionLoggerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface ExceptionLoggerModule {

    @Binds
    fun bindExceptionLogger(exceptionLogger: ExceptionLoggerImpl): ExceptionLogger


}