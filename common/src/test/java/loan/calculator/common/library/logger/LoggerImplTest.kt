/*
 * Created by Elnur Hajiyev on on 7/29/22, 12:14 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.calculator.common.library.logger

import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import timber.log.Timber

class LoggerImplTest() {
    private var logger: Logger = LoggerImpl()
    private val logMessage = "hello"
    private val throwable = Exception()

    @Before
    fun setUp() {
        mockkStatic(Timber::class)
    }

    @Test
    fun testError_withMessage_Calls_Correct() {
        //Act
        logger.error(logMessage)
        //Assert
        verify { Timber.e(logMessage) }
    }

    @Test
    fun testError_withThrowable_Calls_Correct() {
        //Act
        logger.error(throwable, logMessage)
        //Assert
        verify { Timber.e(throwable, logMessage) }
    }

    @Test
    fun testDebug_withMessage_Calls_Correct() {
        //Act
        logger.debug(logMessage)
        //Assert
        verify { Timber.d(logMessage) }
    }

    @Test
    fun testDebug_withThrowable_Calls_Correct() {
        //Act
        logger.debug(throwable, logMessage)
        //Assert
        verify { Timber.d(throwable, logMessage) }
    }

    @Test
    fun testWarn_withMessage_Calls_Correct() {
        //Act
        logger.warn(logMessage)
        //Assert
        verify { Timber.w(logMessage) }
    }

    @Test
    fun testWarn_withThrowable_Calls_Correct() {
        //Act
        logger.warn(throwable, logMessage)
        //Assert
        verify { Timber.w(throwable, logMessage) }
    }

    @Test
    fun testInfo_withMessage_Calls_Correct() {
        //Act
        logger.info(logMessage)
        //Assert
        verify { Timber.i(logMessage) }
    }

    @Test
    fun testInfo_withThrowable_Calls_Correct() {
        //Act
        logger.info(throwable, logMessage)
        //Assert
        verify { Timber.i(throwable, logMessage) }
    }

    @Test
    fun testVerbose_withMessage_Calls_Correct() {
        //Act
        logger.verbose(logMessage)
        //Assert
        verify { Timber.v(logMessage) }
    }

    @Test
    fun testVerbose_withThrowable_Calls_Correct() {
        //Act
        logger.verbose(throwable, logMessage)
        //Assert
        verify { Timber.v(throwable, logMessage) }
    }
}