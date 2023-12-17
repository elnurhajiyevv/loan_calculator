package loan.calculator.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [GetSavedLoanLocalDto::class],
    version = 1,
    exportSchema = false
)
abstract class GetSavedLoanDatabase : RoomDatabase() {
    abstract fun savedLoanDao(): GetSavedLoanDao
}