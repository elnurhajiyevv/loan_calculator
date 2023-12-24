package loan.calculator.save.state

import loan.calculator.domain.entity.saved.GetSavedLoanModel

sealed class SavePageState {

    class GetSavedList(var savedList: List<GetSavedLoanModel>): SavePageState()

    object DeleteSaveLoan: SavePageState()

}