package loan.calculator.save.state

import loan.calculator.domain.entity.home.SavedModel

sealed class SavePageState {

    class GetSavedList(var savedList: List<SavedModel>): SavePageState()

}