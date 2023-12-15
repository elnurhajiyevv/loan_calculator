package loan.exchange.uikit

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.widget.AppCompatEditText


class EditableSeekbarEdittext @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): AppCompatEditText(
    context, attrs, defStyleAttr
) {
    private var mListener: OnEditTextListener? = null

    interface OnEditTextListener {
        fun onEditTextKeyboardDismissed()
        fun onEditTextKeyboardDone()
    }

    init {
        setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (mListener != null) mListener!!.onEditTextKeyboardDone()
                return@OnEditorActionListener true
            }
            false
        })
    }

    fun setOnKeyboardDismissedListener(listener: OnEditTextListener?) {
        mListener = listener
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
            if (mListener != null) mListener!!.onEditTextKeyboardDismissed()
            return false
        }
        return super.dispatchKeyEvent(event)
    }
}