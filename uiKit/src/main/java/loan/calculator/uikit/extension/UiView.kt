package loan.calculator.uikit.extension

import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import loan.calculator.uikit.SelectionSumFormatter

fun EditText.enableSumFormatting(): TextWatcher {
    keyListener = android.text.method.DigitsKeyListener.getInstance("1234567890.")
    val f = SelectionSumFormatter()
    return doOnTextChanged { text, start, before, count ->
        val sumTextModel: SelectionSumFormatter.SumTextModel? = this.selectionEnd.let { selection: Int ->
            f.getSumSelectionAndText(
                text.toString(),
                selection,
            )
        }
        if (sumTextModel?.sumSelection != null) {
            try {
                this.setText(sumTextModel.sumText)
                sumTextModel.sumSelection.let { this.setSelection(it) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

fun TextView.enableSumFormatting() {
    val f = SelectionSumFormatter()
    val sumTextModel: SelectionSumFormatter.SumTextModel? = this.selectionEnd.let { selection: Int ->
        f.getSumSelectionAndText(
            text.toString(),
            selection,
        )
    }
    if (sumTextModel?.sumSelection != null) {
        try {
            this.text = sumTextModel.sumText
            sumTextModel.sumSelection.let { this.setText(it) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}