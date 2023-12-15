package loan.exchange.uikit.bottomsheet

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import loan.exchange.common.extensions.addCorners
import loan.exchange.common.extensions.dp
import loan.exchange.core.base.BaseNotSerializableBottomSheet
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import loan.exchange.domain.entity.home.CurrencyModel
import loan.exchange.uikit.R
import loan.exchange.uikit.adapter.CurrencyAdapter
import loan.exchange.uikit.databinding.DialogFragmentCurrencyBinding
import java.util.Map

class CurrencyMenuBottomSheet : BaseNotSerializableBottomSheet() {

    private var backButton = false
    private lateinit var itemList: List<CurrencyModel>
    private var onItemsSelected: ((CurrencyModel) -> Unit)? = null
    private var onDismiss: (() -> Unit)? = null
    private var onBack: (() -> Unit)? = null

    lateinit var binding: DialogFragmentCurrencyBinding
    lateinit var currencyAdapter: CurrencyAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogFragmentCurrencyBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
        }
        initView()
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun initView() {
        with(binding) {
            mainLayout.addCorners(
                solidColor = R.color.color_pure_white,
                radius = floatArrayOf(24.dp.toFloat(), 24.dp.toFloat(), 24.dp.toFloat(), 24.dp.toFloat(), 0f, 0f, 0f, 0f)
            )
            recyclerViewCard.layoutManager = LinearLayoutManager(context)
            recyclerViewCard.setHasFixedSize(true)

            currencyAdapter = CurrencyAdapter(itemList, CurrencyAdapter.CurrencyItemClick {
                onItemsSelected?.invoke(it)
                this@CurrencyMenuBottomSheet.dismiss()
            })
            recyclerViewCard.adapter = currencyAdapter
            search.setOnSearchAction {
                currencyAdapter.filter(it)
            }
            backButton.setOnClickListener {
                onDismiss?.invoke()
            }

        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss?.invoke()
    }


    class Builder {
        var itemList: List<CurrencyModel>? = null
        var onItemsSelected: ((CurrencyModel) -> Unit)? = null
        var onDismiss: (() -> Unit)? = null
        var onBack: (() -> Unit)? = null

        fun itemList(itemList: () -> List<CurrencyModel>) {
            this.itemList = itemList()
        }

        fun onItemsSelected(onItemsSelected: (CurrencyModel) -> Unit) {
            this.onItemsSelected = onItemsSelected
        }

        fun onDismiss(onDismiss: () -> Unit) {
            this.onDismiss = onDismiss
        }
        fun onBack(onDismiss: () -> Unit) {
            this.onBack = onBack
        }

        fun build(): CurrencyMenuBottomSheet? {
            val bottomSheet = CurrencyMenuBottomSheet()
            bottomSheet.itemList = itemList?:return null
            bottomSheet.onItemsSelected = onItemsSelected
            bottomSheet.onDismiss = onDismiss
            bottomSheet.onBack = onBack
            return bottomSheet
        }
    }


}

fun currencyMenuBottomSheet(lambda: CurrencyMenuBottomSheet.Builder.() -> Unit) =
    CurrencyMenuBottomSheet.Builder().apply(lambda).build()