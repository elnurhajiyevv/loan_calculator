package loan.calculator.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GetSavedLoanDao {

    @Query("SELECT * from saved_loan_table ORDER BY code ASC")
    fun observeSavedLoan(): Flow<List<GetSavedLoanLocalDto>>

    @Query("SELECT * from saved_loan_table WHERE name = :name")
    fun getSavedLoan(name: String): Flow<GetSavedLoanLocalDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSavedLoan(item: GetSavedLoanLocalDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSavedLoans(item: List<GetSavedLoanLocalDto>)


    @Query("DELETE FROM saved_loan_table")
    fun deleteSavedLoanList()

    @Query("DELETE FROM saved_loan_table WHERE name = :name")
    fun deleteSavedLoan(name:String)

    @Transaction
    fun flushAndInsertSavedLoan(
        item: GetSavedLoanLocalDto,
    ) {
        deleteSavedLoanList()
        insertSavedLoan(item = item)
    }
}