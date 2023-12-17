package loan.calculator.di

import loan.calculator.initializers.AppInitializer
import loan.calculator.initializers.DynaTraceInitializer
import loan.calculator.initializers.TimberInitializer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet


@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @IntoSet
    abstract fun bindTimberInitializer(timberInitializer: TimberInitializer): AppInitializer

    @Binds
    @IntoSet
    abstract fun bindDynaTraceInitializer(dynaTraceInitializer: DynaTraceInitializer): AppInitializer
}