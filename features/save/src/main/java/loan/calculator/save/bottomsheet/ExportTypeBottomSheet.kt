package loan.calculator.save.bottomsheet

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import loan.calculator.common.extensions.addCorners
import loan.calculator.common.extensions.dp
import loan.calculator.core.base.BaseNotSerializableBottomSheet
import loan.calculator.domain.entity.saved.ExportTypeModel
import loan.calculator.save.R
import loan.calculator.save.adapter.ExportTypeAdapter
import loan.calculator.save.databinding.ExportTypeBottomSheetBinding
import java.util.*

class ExportTypeBottomSheet: BaseNotSerializableBottomSheet() {


    private var backButton = false
    private lateinit var itemList: List<ExportTypeModel>
    private var onItemsSelected: ((ExportTypeModel) -> Unit)? = null
    private var onDismiss: (() -> Unit)? = null
    private var onBack: (() -> Unit)? = null

    override var showFullscreen: Boolean
        get() = false
        set(value) {}

    lateinit var exportTypeAdapter: ExportTypeAdapter

    private lateinit var binding: ExportTypeBottomSheetBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ExportTypeBottomSheetBinding.inflate(inflater, container, false)
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
            recyclerViewExport.layoutManager = LinearLayoutManager(context)
            recyclerViewExport.setHasFixedSize(true)

            exportTypeAdapter = ExportTypeAdapter(
                itemList,
                ExportTypeAdapter.ExportItemClick {
                    onItemsSelected?.invoke(it)
                    this@ExportTypeBottomSheet.dismiss()
                })
            recyclerViewExport.adapter = exportTypeAdapter
            binding.backButton.setOnClickListener {
                this@ExportTypeBottomSheet.dismiss()
            }

        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss?.invoke()
    }


    /**
     * get Selected Date from Date Picker
     * @return a java.util.Date
     */
    class Builder {
        var itemList: List<ExportTypeModel>? = null
        var onItemsSelected: ((ExportTypeModel) -> Unit)? = null
        var onDismiss: (() -> Unit)? = null
        var onBack: (() -> Unit)? = null

        fun itemList(itemList: () -> List<ExportTypeModel>) {
            this.itemList = itemList()
        }

        fun onItemsSelected(onItemsSelected: (ExportTypeModel) -> Unit) {
            this.onItemsSelected = onItemsSelected
        }

        fun onDismiss(onDismiss: () -> Unit) {
            this.onDismiss = onDismiss
        }
        fun onBack(onDismiss: () -> Unit) {
            this.onBack = onBack
        }

        fun build(): ExportTypeBottomSheet? {
            val bottomSheet = ExportTypeBottomSheet()
            bottomSheet.itemList = itemList?:return null
            bottomSheet.onItemsSelected = onItemsSelected
            bottomSheet.onDismiss = onDismiss
            bottomSheet.onBack = onBack
            return bottomSheet
        }
    }


}
fun exportTypeBottomSheet(lambda: ExportTypeBottomSheet.Builder.() -> Unit) =
    ExportTypeBottomSheet.Builder().apply(lambda).build()
