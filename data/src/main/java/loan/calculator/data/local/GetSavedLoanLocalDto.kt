package loan.calculator.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "saved_loan_table"
)
data class GetSavedLoanLocalDto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "code") val code: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "background") val background: String?,
    @ColumnInfo(name = "src") val src: String?,
    @ColumnInfo(name = "startDate") val startDate: String?,
    @ColumnInfo(name = "paidOff") val paidOff: String?,
    @ColumnInfo(name = "loanAmount") val loanAmount: String?,
    @ColumnInfo(name = "interestRate") val interestRate: String?,
    @ColumnInfo(name = "compoundingFrequency") val compoundingFrequency: String?,
    @ColumnInfo(name = "totalPayment") val totalPayment: String?,
    @ColumnInfo(name = "termInMonth") val termInMonth: Int?,

)
