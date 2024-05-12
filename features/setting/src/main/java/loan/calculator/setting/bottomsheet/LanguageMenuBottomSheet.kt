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
import loan.calculator.domain.entity.home.LanguageModel
import loan.calculator.uikit.R
import loan.calculator.setting.adapter.LanguageAdapter
import loan.calculator.setting.databinding.DialogLanguageBinding

class LanguageMenuBottomSheet : BaseNotSerializableBottomSheet() {

    private var backButton = false
    private lateinit var itemList: List<LanguageModel>
    private var onItemsSelected: ((LanguageModel) -> Unit)? = null
    private var onDismiss: (() -> Unit)? = null
    private var onBack: (() -> Unit)? = null

    lateinit var binding: DialogLanguageBinding
    lateinit var languageAdapter: LanguageAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogLanguageBinding.inflate(inflater, container, false)
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
                solidColor = R.color.adapter_background,
                radius = floatArrayOf(24.dp.toFloat(), 24.dp.toFloat(), 24.dp.toFloat(), 24.dp.toFloat(), 0f, 0f, 0f, 0f)
            )
            recyclerViewLanguage.layoutManager = LinearLayoutManager(context)
            recyclerViewLanguage.setHasFixedSize(true)

            languageAdapter = LanguageAdapter(
                itemList,
                LanguageAdapter.LanguageItemClick {
                    onItemsSelected?.invoke(it)
                    this@LanguageMenuBottomSheet.dismiss()
                })
            recyclerViewLanguage.adapter = languageAdapter
            search.setOnSearchAction {
                languageAdapter.filter(it)
            }
            applyButton.setOnClickListener {
                onDismiss?.invoke()
            }

        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss?.invoke()
    }


    class Builder {
        var itemList: List<LanguageModel>? = null
        var onItemsSelected: ((LanguageModel) -> Unit)? = null
        var onDismiss: (() -> Unit)? = null
        var onBack: (() -> Unit)? = null

        fun itemList(itemList: () -> List<LanguageModel>) {
            this.itemList = itemList()
        }

        fun onItemsSelected(onItemsSelected: (LanguageModel) -> Unit) {
            this.onItemsSelected = onItemsSelected
        }

        fun onDismiss(onDismiss: () -> Unit) {
            this.onDismiss = onDismiss
        }
        fun onBack(onDismiss: () -> Unit) {
            this.onBack = onBack
        }

        fun build(): LanguageMenuBottomSheet? {
            val bottomSheet = LanguageMenuBottomSheet()
            bottomSheet.itemList = itemList?:return null
            bottomSheet.onItemsSelected = onItemsSelected
            bottomSheet.onDismiss = onDismiss
            bottomSheet.onBack = onBack
            return bottomSheet
        }
    }


}

fun languageMenuBottomSheet(lambda: LanguageMenuBottomSheet.Builder.() -> Unit) =
    LanguageMenuBottomSheet.Builder().apply(lambda).build()