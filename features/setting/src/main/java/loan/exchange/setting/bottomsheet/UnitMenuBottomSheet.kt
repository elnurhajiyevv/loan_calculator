package loan.exchange.setting.bottomsheet

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import loan.exchange.common.extensions.addCorners
import loan.exchange.common.extensions.dp
import loan.exchange.core.base.BaseNotSerializableBottomSheet
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import loan.exchange.setting.R
import loan.exchange.setting.adapter.UnitDetailAdapter
import loan.exchange.setting.databinding.DialogFragmentUnitBinding
import loan.exchange.setting.util.UnitConverter

class UnitMenuBottomSheet : BaseNotSerializableBottomSheet() {

    private var backButton = false
    private lateinit var itemList: List<UnitConverter.Unit>
    private var onItemsSelected: ((UnitConverter.Unit) -> Unit)? = null
    private var onDismiss: (() -> Unit)? = null
    private var onBack: (() -> Unit)? = null

    lateinit var binding: DialogFragmentUnitBinding
    lateinit var unitDetailAdapter: UnitDetailAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogFragmentUnitBinding.inflate(inflater, container, false)
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

            unitDetailAdapter = UnitDetailAdapter(UnitDetailAdapter.UnitItemClick {
                onItemsSelected?.invoke(it)
                this@UnitMenuBottomSheet.dismiss()
            })
            unitDetailAdapter.submitList(itemList)
            recyclerViewCard.adapter = unitDetailAdapter
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
        var itemList: List<UnitConverter.Unit>? = null
        var onItemsSelected: ((UnitConverter.Unit) -> Unit)? = null
        var onDismiss: (() -> Unit)? = null
        var onBack: (() -> Unit)? = null

        fun itemList(itemList: () -> List<UnitConverter.Unit>) {
            this.itemList = itemList()
        }

        fun onItemsSelected(onItemsSelected: (UnitConverter.Unit) -> Unit) {
            this.onItemsSelected = onItemsSelected
        }

        fun onDismiss(onDismiss: () -> Unit) {
            this.onDismiss = onDismiss
        }
        fun onBack(onDismiss: () -> Unit) {
            this.onBack = onBack
        }

        fun build(): UnitMenuBottomSheet? {
            val bottomSheet = UnitMenuBottomSheet()
            bottomSheet.itemList = itemList?:return null
            bottomSheet.onItemsSelected = onItemsSelected
            bottomSheet.onDismiss = onDismiss
            bottomSheet.onBack = onBack
            return bottomSheet
        }
    }


}

fun unitMenuBottomSheet(lambda: UnitMenuBottomSheet.Builder.() -> Unit) =
    UnitMenuBottomSheet.Builder().apply(lambda).build()