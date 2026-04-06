package  loan.calculator.loan.viewmodel

import loan.calculator.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import loan.calculator.domain.entity.enum.SELECT_TYPE_LOAN
import loan.calculator.domain.entity.home.SaveLoanObject
import loan.calculator.domain.entity.saved.GetSavedLoanModel
import loan.calculator.domain.entity.unit.IconModel
import loan.calculator.domain.usecase.savepage.GetIconModelUseCase
import loan.calculator.domain.usecase.savepage.InsertSaveLoanUseCase
import loan.calculator.domain.usecase.savepage.SetIconModelUseCase
import loan.calculator.domain.usecase.settingpage.GetCurrencyUseCase
import loan.calculator.loan.effect.AmortizationPageEffect
import loan.calculator.loan.effect.LoanPageEffect
import loan.calculator.loan.state.AmortizationPageState
import loan.calculator.uikit.extension.getImageBackgroundColor
import javax.inject.Inject

@HiltViewModel
class AmortizationPageViewModel @Inject constructor(
    private val getIconModelUseCase: GetIconModelUseCase,
    private val setIconModelUseCase: SetIconModelUseCase,
    private val insertSaveLoanUseCase: InsertSaveLoanUseCase
) : BaseViewModel<AmortizationPageState, AmortizationPageEffect>() {

    var selectedType = SELECT_TYPE_LOAN.BLOCK.type
    val saveLoanObject = SaveLoanObject(
        "0", "", "", "", "", "," +
                "", "", ""
    )

    fun calculateMonthly(total: Double, period: Double): Double {
        return total / period
    }

    fun calculateTotal(amount: Double, rate: Double, period: Double): Double {
        var partTotal = ((((rate * 57) / 100) * amount) / 100) * ((period / 12))
        return partTotal + amount
    }

    fun calculateInterest(total: Double, amount: Double): Double {
        return total - amount
    }

    fun getIconModelList() {
        var list = arrayListOf<IconModel>()
        list.clear()
        launchAll {
            list.add(
                IconModel(
                    iconResource = SELECT_TYPE_LOAN.BLOCK,
                    backgroundColor = SELECT_TYPE_LOAN.BLOCK.type.getImageBackgroundColor(),
                    isSelected = true
                ),
            )

            list.add(
                IconModel(
                    iconResource = SELECT_TYPE_LOAN.PHONE,
                    backgroundColor = SELECT_TYPE_LOAN.PHONE.type.getImageBackgroundColor(),
                )
            )

            list.add(
                IconModel(
                    iconResource = SELECT_TYPE_LOAN.CAMERA,
                    backgroundColor = SELECT_TYPE_LOAN.CAMERA.type.getImageBackgroundColor(),
                )
            )

            list.add(
                IconModel(
                    iconResource = SELECT_TYPE_LOAN.COMPUTER,
                    backgroundColor = SELECT_TYPE_LOAN.COMPUTER.type.getImageBackgroundColor(),
                )
            )

            list.add(
                IconModel(
                    iconResource = SELECT_TYPE_LOAN.HOME,
                    backgroundColor = SELECT_TYPE_LOAN.HOME.type.getImageBackgroundColor(),
                )
            )

            list.add(
                IconModel(
                    iconResource = SELECT_TYPE_LOAN.GAMING,
                    backgroundColor = SELECT_TYPE_LOAN.GAMING.type.getImageBackgroundColor(),
                )
            )

            list.add(
                IconModel(
                    iconResource = SELECT_TYPE_LOAN.HEADSET,
                    backgroundColor = SELECT_TYPE_LOAN.HEADSET.type.getImageBackgroundColor(),
                )
            )

            list.add(
                IconModel(
                    iconResource = SELECT_TYPE_LOAN.MODEM,
                    backgroundColor = SELECT_TYPE_LOAN.MODEM.type.getImageBackgroundColor(),
                )
            )

            list.add(
                IconModel(
                    iconResource = SELECT_TYPE_LOAN.PRINTER,
                    backgroundColor = SELECT_TYPE_LOAN.PRINTER.type.getImageBackgroundColor(),
                )
            )

            list.add(
                IconModel(
                    iconResource = SELECT_TYPE_LOAN.WATCH,
                    backgroundColor = SELECT_TYPE_LOAN.WATCH.type.getImageBackgroundColor(),
                )
            )
        }
        list.forEach {
            if (it == getIconModel())
                it.isSelected = true
        }
        postEffect(effect = AmortizationPageEffect.ListOfIconModel(list))
    }

    fun getIconModel() = getIconModelUseCase.invoke(Unit)

    fun setIconModel(model: IconModel) {
        selectedType = model.iconResource.type
        setIconModelUseCase.invoke(SetIconModelUseCase.Params(model = model))
    }

    fun insertSavedLoan(model: GetSavedLoanModel) {
        insertSaveLoanUseCase.launch(InsertSaveLoanUseCase.Params(model = model)) {
            onSuccess = {
                postEffect(AmortizationPageEffect.InsertSavedLoan(model))
            }
        }
    }

    fun getPeriodInMonth(year: Int, month: Int): Int{
        return (year * 12) + month
    }
}
