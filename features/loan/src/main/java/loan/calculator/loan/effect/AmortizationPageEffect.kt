/*
 * Created by Elnur Hajiyev on on 8/6/22, 2:36 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.calculator.loan.effect

import loan.calculator.domain.entity.saved.GetSavedLoanModel
import loan.calculator.domain.entity.unit.IconModel

open class AmortizationPageEffect {
    class ListOfIconModel(val list: List<IconModel>): AmortizationPageEffect()

    class InsertSavedLoan(var model: GetSavedLoanModel): AmortizationPageEffect()
}