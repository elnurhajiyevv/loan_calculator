/*
 * Created by Elnur Hajiyev on on 7/29/22, 12:52 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.calculator.common.library.exceptionhandler

import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class ExceptionLoggerImplTest {

    private var logger: ExceptionLogger = ExceptionLoggerImpl()
    private val throwable = Exception()
    private val firebaseCrashlytics = mockk<FirebaseCrashlytics>()
    val key = "Test"

    @Before
    fun setUp() {
        mockkStatic(FirebaseCrashlytics::class)
        every { FirebaseCrashlytics.getInstance() } returns firebaseCrashlytics
    }

    @Test
    fun testLogException_calls_correct() {
        //Arrange
        every { firebaseCrashlytics.recordException(throwable) } returns Unit
        //Act
        logger.logException(throwable)
        //Assert
        verify { firebaseCrashlytics.recordException(throwable) }
    }

    @Test
    fun testSetLogValue_String_calls_correct() {
        //Arrange
        val value = "namiq"
        every { firebaseCrashlytics.setCustomKey(key, value) } returns Unit
        //Act
        logger.setLogValue(key, value)
        //Assert
        verify { firebaseCrashlytics.setCustomKey(key, value) }
    }

    @Test
    fun testSetLogValue_Int_calls_correct() {
        //Arrange
        val value = 1
        every { firebaseCrashlytics.setCustomKey(key, value) } returns Unit
        //Act
        logger.setLogValue(key, value)
        //Assert
        verify { firebaseCrashlytics.setCustomKey(key, value) }
    }

    @Test
    fun testSetLogValue_Float_calls_correct() {
        //Arrange
        val value = 3.5f
        every { firebaseCrashlytics.setCustomKey(key, value) } returns Unit
        //Act
        logger.setLogValue(key, value)
        //Assert
        verify { firebaseCrashlytics.setCustomKey(key, value) }
    }

    @Test
    fun testSetLogValue_Long_calls_correct() {
        //Arrange
        val value = 222L
        every { firebaseCrashlytics.setCustomKey(key, value) } returns Unit
        //Act
        logger.setLogValue(key, value)
        //Assert
        verify { firebaseCrashlytics.setCustomKey(key, value) }
    }

    @Test
    fun testSetLogValue_Boolean_calls_correct() {
        //Arrange
        val value = true
        every { firebaseCrashlytics.setCustomKey(key, value) } returns Unit
        //Act
        logger.setLogValue(key, value)
        //Assert
        verify { firebaseCrashlytics.setCustomKey(key, value) }
    }

    @Test
    fun testSetLogValue_Double_calls_correct() {
        //Arrange
        val value = 3.3
        every { firebaseCrashlytics.setCustomKey(key, value) } returns Unit
        //Act
        logger.setLogValue(key, value)
        //Assert
        verify { firebaseCrashlytics.setCustomKey(key, value) }
    }
}