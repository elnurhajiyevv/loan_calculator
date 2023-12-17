/*
 * Created by Elnur Hajiyev on on 7/16/22, 12:16 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.calculator.common.library.logger

interface Logger {


    // not implemented yet
    fun verbose(throwable: Throwable, message: String)
    fun verbose(message: String)
    fun error(message: String)
    fun error(throwable: Throwable, message: String)
    fun debug(message: String)
    fun debug(throwable: Throwable, message: String)
    fun warn(message: String)
    fun warn(throwable: Throwable, message: String)
    fun info(message: String)
    fun info(throwable: Throwable, message: String)
}