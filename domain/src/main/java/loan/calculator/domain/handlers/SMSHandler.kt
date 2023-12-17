/*
 * Created by Elnur Hajiyev on on 8/11/22, 4:22 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.calculator.domain.handlers

interface SMSHandler {
    fun setSMSListener(otpListener: (otpCode: String) -> Unit)
}