package loan.calculator.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GetSavedLoanDao {

    @Query("SELECT * from saved_loan_table ORDER BY code ASC")
    fun observeSavedLoan(): Flow<List<GetSavedLoanLocalDto>>

    @Query("SELECT * from saved_loan_table WHERE name = :name")
    suspend fun getSavedLoan(name: String): GetSavedLoanLocalDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedLoan(item: GetSavedLoanLocalDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedLoans(item: List<GetSavedLoanLocalDto>)


    @Query("DELETE FROM saved_loan_table")
    suspend fun deleteSavedLoanList()

    @Query("DELETE FROM saved_loan_table WHERE name = :name")
    suspend fun deleteSavedLoan(name:String)

    @Transaction
    suspend fun flushAndInsertSavedLoan(
        item: GetSavedLoanLocalDto,
    ) {
        deleteSavedLoanList()
        insertSavedLoan(item = item)
    }
}