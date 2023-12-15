/*
 * Created by Elnur Hajiyev on on 7/26/22, 12:24 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.exchange.common.library.exceptionhandler

import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject

/**
 * This class is responsible to log handled Exceptions and set logs before exception handled
 * @see ExceptionLogger
 */
class ExceptionLoggerImpl @Inject constructor() : ExceptionLogger {

    /**
     * Log exception to firebase crashlytics logger
     */
    override fun logException(exception: Throwable) {
        FirebaseCrashlytics.getInstance().recordException(exception)
    }

    /**
     * sets custom int field
     */
    override fun setLogValue(key: String, value: Int) {
        FirebaseCrashlytics.getInstance().setCustomKey(key, value)
    }

    /**
     * sets custom float field
     */
    override fun setLogValue(key: String, value: Float) {
        FirebaseCrashlytics.getInstance().setCustomKey(key, value)
    }

    /**
     * sets custom double field
     */
    override fun setLogValue(key: String, value: Double) {
        FirebaseCrashlytics.getInstance().setCustomKey(key, value)
    }

    /**
     * sets custom long field
     */
    override fun setLogValue(key: String, value: Long) {
        FirebaseCrashlytics.getInstance().setCustomKey(key, value)
    }

    /**
     * sets custom string field
     */
    override fun setLogValue(key: String, value: String) {
        FirebaseCrashlytics.getInstance().setCustomKey(key, value)
    }

    /**
     * sets custom boolean field
     */
    override fun setLogValue(key: String, value: Boolean) {
        FirebaseCrashlytics.getInstance().setCustomKey(key, value)
    }

}