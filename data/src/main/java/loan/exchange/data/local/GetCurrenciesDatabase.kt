package loan.exchange.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [GetCurrenciesLocalDto::class],
    version = 2,
    exportSchema = false
)
abstract class GetCurrenciesDatabase : RoomDatabase() {
    abstract fun currencyDao(): GetCurrenciesDao
}