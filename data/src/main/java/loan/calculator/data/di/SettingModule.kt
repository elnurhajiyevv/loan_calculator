package loan.calculator.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import loan.calculator.data.repository.DeviceConfigRepositoryImpl
import loan.calculator.data.repository.SettingRepositoryImpl
import loan.calculator.domain.repository.DeviceConfigRepository
import loan.calculator.domain.repository.SettingRepository

@Module
@InstallIn(SingletonComponent::class)
interface SettingModule {

    @Binds
    fun bindSettingRepository(settingRepository: SettingRepositoryImpl): SettingRepository

    @Binds
    fun bindDeviceConfigRepository(deviceConfigRepository: DeviceConfigRepositoryImpl): DeviceConfigRepository
}