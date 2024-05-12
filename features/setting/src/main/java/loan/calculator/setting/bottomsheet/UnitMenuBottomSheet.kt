package loan.calculator.setting.bottomsheet

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
import loan.calculator.uikit.R
import loan.calculator.setting.databinding.DialogFragmentUnitBinding

class UnitMenuBottomSheet : BaseNotSerializableBottomSheet() {

    private var backButton = false
    private lateinit var itemList: List<String>
    private var onItemsSelected: ((String) -> Unit)? = null
    private var onDismiss: (() -> Unit)? = null
    private var onBack: (() -> Unit)? = null

    lateinit var binding: DialogFragmentUnitBinding
    //lateinit var unitDetailAdapter: UnitDetailAdapter

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

            /*unitDetailAdapter = UnitDetailAdapter(UnitDetailAdapter.UnitItemClick {
                onItemsSelected?.invoke(it)
                this@UnitMenuBottomSheet.dismiss()
            })*/
            /*unitDetailAdapter.submitList(itemList)
            recyclerViewCard.adapter = unitDetailAdapter*/
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
        var itemList: List<String>? = null
        var onItemsSelected: ((String) -> Unit)? = null
        var onDismiss: (() -> Unit)? = null
        var onBack: (() -> Unit)? = null

        fun itemList(itemList: () -> List<String>) {
            this.itemList = itemList()
        }

        fun onItemsSelected(onItemsSelected: (String) -> Unit) {
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