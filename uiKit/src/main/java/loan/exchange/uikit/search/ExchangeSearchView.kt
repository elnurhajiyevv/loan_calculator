package loan.exchange.uikit.search

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import loan.exchange.common.extensions.addBorder
import loan.exchange.common.extensions.dp
import loan.exchange.common.extensions.gone
import loan.exchange.common.extensions.show
import loan.exchange.core.extension.hideKeyboard
import loan.exchange.core.extension.showKeyboard
import loan.exchange.uikit.R
import loan.exchange.uikit.databinding.ExchangeSearchViewBinding

class ExchangeSearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var hintText: String? = null

    private var binding: ExchangeSearchViewBinding = ExchangeSearchViewBinding.inflate(LayoutInflater.from(context), this, true)
    private var mOnSearchAction: ((searchTitle: String) -> Unit)? = null
    private var mOnSearchActionClose: (() -> Unit)? = null

    init {
        context.obtainStyledAttributes(attrs, R.styleable.ExchangeSearchView, defStyleAttr, R.style.light_caption1).apply {
            hintText = getString(R.styleable.ExchangeSearchView_exchangeSearchView_hint).toString()
            recycle()
        }

        binding.mainLayout.addBorder(strokeColor = R.color.color_secondary, solidColor = R.color.color_pure_white, radius = 2.dp.toFloat(), strokeWidth = 1.dp)

        binding.searchMessage.setOnEditorActionListener { _, _, _ ->
            false
        }

        binding.searchMessage.setOnFocusChangeListener { v, hasFocus ->
            changeSearchIconStatus(
                if(hasFocus) SearchIconStatus.ONFOCUS else SearchIconStatus.OFFFOCUS,
                hasFocus
            )
        }

        binding.searchMessage.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do Nothing
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mOnSearchAction?.invoke(s.toString())
            }

            override fun afterTextChanged(s: Editable) {
                // Do Nothing
            }

        })
        binding.searchAction.setOnClickListener {
            clearText()
            binding.searchMessage.clearFocus()
            changeSearchIconStatus(SearchIconStatus.OFFFOCUS,false)
            mOnSearchActionClose?.invoke()
        }
        setHintText(hintText)
    }

    fun setOnSearchAction(onSearch: (searchTitle: String) -> Unit) {
        mOnSearchAction = onSearch
    }

    fun setOnSearchActionClose(onSearchClose:() -> Unit) {
        mOnSearchActionClose = onSearchClose
    }

    private fun changeSearchIconStatus(status: SearchIconStatus, hasFocus: Boolean){
        when(status){
            SearchIconStatus.OFFFOCUS -> {
                binding.searchAction.gone()
                hideKeyboard()
                unselect()
            }
            SearchIconStatus.ONFOCUS ->{
                showKeyboard()
                binding.searchAction.show()
            }
        }
        binding.mainLayout.addBorder(strokeColor = if(hasFocus) R.color.solid_blue else R.color.color_secondary, radius = 2.dp.toFloat(), strokeWidth = if(hasFocus) 1.dp else 1.dp, solidColor = R.color.color_pure_white)
        binding.searchIcon.setImageResource(if(hasFocus) R.drawable.ic_search_focus else R.drawable.ic_search)
    }
    enum class SearchIconStatus{
        OFFFOCUS,ONFOCUS
    }

    private fun clearText(){
        binding.searchMessage.text?.clear()
    }

    fun unselect(){
        binding.mainLayout.addBorder(strokeColor = R.color.color_secondary, solidColor = R.color.color_pure_white, radius = 2.dp.toFloat(), strokeWidth = 1.dp)
    }

    private fun setHintText(text: String?){
        binding.searchMessage.hint = text
    }

}