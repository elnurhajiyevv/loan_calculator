package loan.calculator.data.di

import android.content.Context
import androidx.room.Room
import loan.calculator.data.local.GetSavedLoanDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import loan.calculator.data.local.GetSavedLoanDatabase
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
    fun provideCurrencyDao(database: GetSavedLoanDatabase): GetSavedLoanDao {
        return database.savedLoanDao()
    }

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideCountriesDatabase(@ApplicationContext context: Context): GetSavedLoanDatabase {
        return Room.databaseBuilder(context, GetSavedLoanDatabase::class.java, "savedLoan-db")
            .fallbackToDestructiveMigration()
            .build()
    }

}