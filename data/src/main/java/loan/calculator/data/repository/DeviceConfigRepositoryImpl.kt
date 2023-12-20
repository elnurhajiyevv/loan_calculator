package loan.calculator.data.repository

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import dagger.hilt.android.qualifiers.ApplicationContext
import loan.calculator.domain.repository.DeviceConfigRepository
import javax.inject.Inject

class DeviceConfigRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context) :
    DeviceConfigRepository {

    override suspend fun getAppVersion(): String {
        val packageManager: PackageManager = context.packageManager
        val info: PackageInfo = packageManager.getPackageInfo(context.packageName, 0)
        return info.versionName
    }

    override suspend fun getAppPackageName(): String {
        val packageManager: PackageManager = context.packageManager
        val info: PackageInfo = packageManager.getPackageInfo(context.packageName, 0)
        return info.packageName
    }
}