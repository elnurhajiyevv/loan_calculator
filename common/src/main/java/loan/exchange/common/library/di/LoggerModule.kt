/*
 * Created by Elnur Hajiyev on on 7/29/22, 11:26 AM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.exchange.common.library.di

import loan.exchange.common.library.logger.Logger
import loan.exchange.common.library.logger.LoggerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LoggerModule {

    @Binds
    fun bindLogger(loggerImpl: LoggerImpl): Logger
}