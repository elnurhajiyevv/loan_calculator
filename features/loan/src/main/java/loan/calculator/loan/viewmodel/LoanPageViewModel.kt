package  loan.calculator.loan.viewmodel

import loan.calculator.core.base.BaseViewModel
import loan.calculator.loan.effect.LoanPageEffect
import loan.calculator.loan.state.LoanPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import loan.calculator.domain.entity.enum.SELECT_TYPE_LOAN
import loan.calculator.domain.entity.saved.GetSavedLoanModel
import loan.calculator.domain.entity.unit.IconModel
import loan.calculator.domain.usecase.savepage.GetIconModelUseCase
import loan.calculator.domain.usecase.savepage.InsertSaveLoanUseCase
import loan.calculator.domain.usecase.savepage.SetIconModelUseCase
import loan.calculator.domain.usecase.settingpage.GetShowCaseUseCase
import loan.calculator.domain.usecase.settingpage.SetShowCaseUseCase
import loan.calculator.domain.util.SELECT_PART
import loan.calculator.uikit.extension.getImageBackgroundColor
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class LoanPageViewModel @Inject constructor(
    private val getShowCaseUseCase: GetShowCaseUseCase,
    private val setShowCaseUseCase: SetShowCaseUseCase,
    private val insertSaveLoanUseCase: InsertSaveLoanUseCase,
    private val getIconModelUseCase: GetIconModelUseCase,
    private val setIconModelUseCase: SetIconModelUseCase
    ) : BaseViewModel<LoanPageState, LoanPageEffect>() {

    var selectedStartDate = Date()

    var selectedType = SELECT_TYPE_LOAN.BLOCK.type

    fun getShowCase() = getShowCaseUseCase.invoke(Unit)

    fun setShowCase(value:Boolean){
        setShowCaseUseCase.invoke(SetShowCaseUseCase.Params(value = value))
    }

    var setSelection = SELECT_PART.PAYMENT

    var totalInterest = 6618.55
    var totalPayment = 106618.55

    fun getIconModelList(){
        var list = arrayListOf<IconModel>()
        list.clear()
        launchAll {
            list.add(
                IconModel(
                    iconResource = SELECT_TYPE_LOAN.BLOCK,
                    backgroundColor = SELECT_TYPE_LOAN.BLOCK.type.getImageBackgroundColor(),
                    isSelected = true),
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
            if(it == getIconModel())
                it.isSelected = true
        }
        postEffect(effect = LoanPageEffect.ListOfIconModel(list))
    }

    fun getIconModel() = getIconModelUseCase.invoke(Unit)

    fun setIconModel(model:IconModel){
        selectedType = model.iconResource.type
        setIconModelUseCase.invoke(SetIconModelUseCase.Params(model = model))
    }

    fun insertSavedLoan(model: GetSavedLoanModel) {
        insertSaveLoanUseCase.launch(InsertSaveLoanUseCase.Params(model = model)) {
            onSuccess = {
                postEffect(LoanPageEffect.InsertSavedLoan(model))
            }
        }
    }
    fun convertMonthToYear(month: Int): Int{
        return month / 12
    }

    fun convertedMonth(month: Int): Int{
        return month % 12
    }

    fun getPeriodInMonth(year: Int, month: Int): Int{
        return (year * 12) + month
    }
}
