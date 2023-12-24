/*
 * Created by Elnur Hajiyev on on 8/6/22, 2:36 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.calculator.loan.effect

import loan.calculator.domain.entity.saved.GetSavedLoanModel
import loan.calculator.loan.state.SaveDialogState

open class SaveDialogEffect {
    class InsertSavedLoan(var model: GetSavedLoanModel): SaveDialogEffect()
}