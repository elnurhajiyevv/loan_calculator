package loan.exchange.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GetCurrenciesDao {

    @Query("SELECT * from currency_table")
    fun observeCurrenciesResponse(): Flow<GetCurrenciesLocalDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrenciesResponse(response: GetCurrenciesLocalDto)


    @Query("DELETE FROM currency_table")
    suspend fun clearCurrencyResponse()

    @Transaction
    suspend fun flushAndInsertLatestExchangeResponse(
        response: GetCurrenciesLocalDto,
    ) {
        clearCurrencyResponse()
        insertCurrenciesResponse(response = response)
    }
}