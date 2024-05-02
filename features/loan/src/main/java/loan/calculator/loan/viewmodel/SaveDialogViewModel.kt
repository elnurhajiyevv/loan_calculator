package  loan.calculator.loan.viewmodel

import android.content.Context
import loan.calculator.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import loan.calculator.domain.entity.enum.SELECT_TYPE_LOAN
import loan.calculator.domain.entity.home.LanguageModel
import loan.calculator.domain.entity.saved.GetSavedLoanModel
import loan.calculator.domain.entity.unit.IconModel
import loan.calculator.domain.usecase.savepage.GetIconModelUseCase
import loan.calculator.domain.usecase.savepage.InsertSaveLoanUseCase
import loan.calculator.domain.usecase.savepage.SetIconModelUseCase
import loan.calculator.domain.usecase.settingpage.GetLanguageUseCase
import loan.calculator.domain.usecase.settingpage.SetLanguageUseCase
import loan.calculator.loan.effect.SaveDialogEffect
import loan.calculator.loan.state.LoanPageState
import loan.calculator.loan.state.SaveDialogState
import loan.calculator.loan.view.SaveDialog
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

    var selectedType = SELECT_TYPE_LOAN.HOME.type

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
                    iconResource = SELECT_TYPE_LOAN.HOME,
                    backgroundColor = SELECT_TYPE_LOAN.HOME.type.getImageBackgroundColor(context),
                    isSelected = true),
            )

            list.add(
                IconModel(
                    iconResource = SELECT_TYPE_LOAN.CAR,
                    backgroundColor = SELECT_TYPE_LOAN.CAR.type.getImageBackgroundColor(context),
                    )
            )

            list.add(
                IconModel(
                    iconResource = SELECT_TYPE_LOAN.LAPTOP,
                    backgroundColor = SELECT_TYPE_LOAN.LAPTOP.type.getImageBackgroundColor(context),
                    )
            )

            list.add(
                IconModel(
                    iconResource = SELECT_TYPE_LOAN.PHONE,
                    backgroundColor = SELECT_TYPE_LOAN.PHONE.type.getImageBackgroundColor(context),
                    ))

            list.add(
                IconModel(
                    iconResource = SELECT_TYPE_LOAN.CARD,
                    backgroundColor = SELECT_TYPE_LOAN.CARD.type.getImageBackgroundColor(context),
                    )
            )

            list.add(
                IconModel(
                    iconResource = SELECT_TYPE_LOAN.BUILDING,
                    backgroundColor = SELECT_TYPE_LOAN.BUILDING.type.getImageBackgroundColor(context),
                    )
            )

            list.add(
                IconModel(
                    iconResource = SELECT_TYPE_LOAN.STUDY,
                    backgroundColor = SELECT_TYPE_LOAN.STUDY.type.getImageBackgroundColor(context),
                    )
            )

            list.add(
                IconModel(
                    iconResource = SELECT_TYPE_LOAN.SPORT,
                    backgroundColor = SELECT_TYPE_LOAN.SPORT.type.getImageBackgroundColor(context),
                    )
            )

            list.add(
                IconModel(
                    iconResource = SELECT_TYPE_LOAN.HEALTY,
                    backgroundColor = SELECT_TYPE_LOAN.HEALTY.type.getImageBackgroundColor(context),
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
