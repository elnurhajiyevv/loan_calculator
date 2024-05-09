package  loan.calculator.loan.viewmodel

import android.content.Context
import loan.calculator.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import loan.calculator.domain.entity.enum.SELECT_TYPE_LOAN
import loan.calculator.domain.entity.saved.GetSavedLoanModel
import loan.calculator.domain.entity.unit.IconModel
import loan.calculator.domain.usecase.savepage.GetIconModelUseCase
import loan.calculator.domain.usecase.savepage.InsertSaveLoanUseCase
import loan.calculator.domain.usecase.savepage.SetIconModelUseCase
import loan.calculator.loan.effect.SaveDialogEffect
import loan.calculator.loan.state.SaveDialogState
import loan.calculator.uikit.extension.getImageBackgroundColor

import java.util.Date
import javax.inject.Inject

@HiltViewModel
class SaveDialogViewModel @Inject constructor(
    private val insertSaveLoanUseCase: InsertSaveLoanUseCase,
    private val getIconModelUseCase: GetIconModelUseCase,
    private val setIconModelUseCase: SetIconModelUseCase
) : BaseViewModel<SaveDialogState, SaveDialogEffect>() {

    var selectedStartDate = Date()

    var selectedType = SELECT_TYPE_LOAN.BLOCK.type

    fun insertSavedLoan(model: GetSavedLoanModel) {
        insertSaveLoanUseCase.launch(InsertSaveLoanUseCase.Params(model = model)) {
            onSuccess = {
                postEffect(SaveDialogEffect.InsertSavedLoan(model))
            }
        }
    }

    fun getIconModelList(context: Context){
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
                    ))

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
        postState(state = SaveDialogState.ListOfIconModel(list))
    }

    fun getIconModel() = getIconModelUseCase.invoke(Unit)

    fun setIconModel(model:IconModel){
        selectedType = model.iconResource.type
        setIconModelUseCase.invoke(SetIconModelUseCase.Params(model = model))
    }

}
