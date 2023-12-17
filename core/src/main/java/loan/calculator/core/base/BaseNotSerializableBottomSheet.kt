package loan.calculator.core.base

import android.os.Bundle

abstract class BaseNotSerializableBottomSheet : BaseBottomSheetDialog() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            // If activity has been recreated, dismiss bottom sheet
            // because not serializable parameters are lost.
            dismiss()
        }
    }
}