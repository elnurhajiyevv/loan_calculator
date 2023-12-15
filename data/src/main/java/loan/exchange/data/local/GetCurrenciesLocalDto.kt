package loan.exchange.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "currency_table"
)
data class GetCurrenciesLocalDto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "success") val success: Boolean?,
    @ColumnInfo(name = "result") val result: String?
)
