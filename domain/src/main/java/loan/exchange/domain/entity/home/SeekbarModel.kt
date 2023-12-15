package loan.exchange.domain.entity.home

data class SeekbarModel(
    var name: SeekbarName,
    var min: Int = 1,
    var max: Int = 10
)

enum class SeekbarName(value: String){
    LOAN_AMOUNT("loan_amount"),
    LOAN_RATE("loan_rate"),
    LOAN_PERIOD("loan_period")
}
