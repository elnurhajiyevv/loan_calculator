package loan.exchange.data.di

import android.content.Context
import androidx.room.Room
import loan.exchange.data.local.GetCurrenciesDatabase
import loan.exchange.data.local.GetCurrenciesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class DataModule{

    @Provides
    fun provideDispatchersIO(): CoroutineContext {
        return Dispatchers.IO
    }

    @Provides
    fun provideCurrencyDao(database: GetCurrenciesDatabase): GetCurrenciesDao {
        return database.currencyDao()
    }

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideCountriesDatabase(@ApplicationContext context: Context): GetCurrenciesDatabase {
        return Room.databaseBuilder(context, GetCurrenciesDatabase::class.java, "countries-db")
            .fallbackToDestructiveMigration()
            .build()
    }

}