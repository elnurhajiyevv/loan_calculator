/*
 * Created by Elnur Hajiyev on on 7/16/22, 12:17 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.exchange.common.library.logger

import androidx.viewbinding.BuildConfig
import timber.log.Timber
import javax.inject.Inject

/**
 * This class is responsible to create logs. All the application logs should come from here
 * init starts debug tree timber and you can implement all the precesses after that
 */
class LoggerImpl @Inject constructor(): Logger {

    init {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    /**
     * Error case logs
     * @param message as message to show
     */
    override fun error(message: String) {
        Timber.e(message)
    }

    /**
     * Error case logs
     * @param message as message to show
     * @param throwable show exceptions
     */
    override fun error(throwable: Throwable, message: String) {
        Timber.e(throwable, message)
    }

    /**
     * Debug case logs
     * @param message as message to show
     */
    override fun debug(message: String) {
        Timber.d(message)
    }


    /**
     * Debug case logs
     * @param message as message to show
     * @param throwable show exceptions
     */
    override fun debug(throwable: Throwable, message: String) {
        Timber.d(throwable, message)
    }

    /**
     * Warn case logs
     * @param message as message to show
     */
    override fun warn(message: String) {
        Timber.w(message)
    }

    /**
     * Warning case logs
     * @param message as message to show
     * @param throwable show exceptions
     */
    override fun warn(throwable: Throwable, message: String) {
        Timber.w(throwable, message)
    }

    /**
     * Info case logs
     * @param message as message to show
     */
    override fun info(message: String) {
        Timber.i(message)
    }

    /**
     * Info case logs
     * @param message as message to show
     * @param throwable show exceptions
     */
    override fun info(throwable: Throwable, message: String) {
        Timber.i(throwable, message)
    }

    /**
     * Verbose case logs
     * @param message as message to show
     */
    override fun verbose(message: String) {
        Timber.v(message)
    }

    /**
     * Verbose case logs
     * @param message as message to show
     * @param throwable show exceptions
     */
    override fun verbose(throwable: Throwable, message: String) {
        Timber.v(throwable, message)
    }

}