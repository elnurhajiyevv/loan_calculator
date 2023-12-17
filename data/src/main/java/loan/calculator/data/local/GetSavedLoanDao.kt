package loan.calculator.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GetSavedLoanDao {

    @Query("SELECT * from saved_loan_table")
    fun observeSavedLoanResponse(): Flow<GetSavedLoanLocalDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedLoanResponse(response: GetSavedLoanLocalDto)


    @Query("DELETE FROM saved_loan_table")
    suspend fun clearSavedLoanResponse()

    @Transaction
    suspend fun flushAndInsertSavedLoanResponse(
        response: GetSavedLoanLocalDto,
    ) {
        clearSavedLoanResponse()
        insertSavedLoanResponse(response = response)
    }
}