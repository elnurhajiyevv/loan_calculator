package loan.calculator.domain.repository

interface DeviceConfigRepository {
    suspend fun getAppVersion(): String
    suspend fun getAppPackageName(): String
}
