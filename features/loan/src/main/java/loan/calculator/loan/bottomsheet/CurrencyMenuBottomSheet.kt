package loan.calculator.loan.bottomsheet

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import loan.calculator.common.extensions.addCorners
import loan.calculator.common.extensions.dp
import loan.calculator.core.base.BaseNotSerializableBottomSheet
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import loan.calculator.domain.entity.home.SavedModel
import loan.calculator.loan.R
import loan.calculator.loan.databinding.DialogFragmentCurrencyBinding


class CurrencyMenuBottomSheet : BaseNotSerializableBottomSheet() {

    private var backButton = false
    private lateinit var itemList: List<SavedModel>
    private var onItemsSelected: ((SavedModel) -> Unit)? = null
    private var onDismiss: (() -> Unit)? = null
    private var onBack: (() -> Unit)? = null

    lateinit var binding: DialogFragmentCurrencyBinding
    lateinit var currencyAdapter: loan.calculator.loan.adapter.CurrencyAdapter

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

            currencyAdapter = loan.calculator.loan.adapter.CurrencyAdapter(
                itemList,
                loan.calculator.loan.adapter.CurrencyAdapter.CurrencyItemClick {
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
        var itemList: List<SavedModel>? = null
        var onItemsSelected: ((SavedModel) -> Unit)? = null
        var onDismiss: (() -> Unit)? = null
        var onBack: (() -> Unit)? = null

        fun itemList(itemList: () -> List<SavedModel>) {
            this.itemList = itemList()
        }

        fun onItemsSelected(onItemsSelected: (SavedModel) -> Unit) {
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