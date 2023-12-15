/*
 * Created by Elnur Hajiyev on on 7/27/22, 2:23 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.exchange.common.library.di

import loan.exchange.common.library.di.annotations.EncryptedSharedPrefs
import loan.exchange.common.library.di.annotations.MainSharedPrefs
import loan.exchange.common.library.prefs.base.BasePreferences
import loan.exchange.common.library.prefs.sharedprefs.EncryptedSharedPreference
import loan.exchange.common.library.prefs.sharedprefs.MainSharedPreferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SharedPrefsModule {

    @Binds
    @MainSharedPrefs
    @Singleton
    fun bindMainSharedPrefs(mainSharedPreferences: MainSharedPreferences): BasePreferences

    @Binds
    @EncryptedSharedPrefs
    @Singleton
    fun bindEncryptedSharedPrefs(encryptedSharedPreference: EncryptedSharedPreference): BasePreferences

}