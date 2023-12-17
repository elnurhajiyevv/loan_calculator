/*
 * Created by Elnur Hajiyev on on 7/26/22, 12:24 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.calculator.common.library.exceptionhandler

/**
 * This class is responsible to log handled Exceptions and set logs before exception handled
 * @see ExceptionLogger
 */

interface ExceptionLogger {
    fun logException(exception: Throwable)
    fun setLogValue(key: String, value: Int)
    fun setLogValue(key: String, value: Float)
    fun setLogValue(key: String, value: Double)
    fun setLogValue(key: String, value: Long)
    fun setLogValue(key: String, value: String)
    fun setLogValue(key: String, value: Boolean)
}