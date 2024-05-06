package loan.calculator.save.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import loan.calculator.common.extensions.gone
import loan.calculator.common.extensions.show
import loan.calculator.core.base.BaseAdapter
import loan.calculator.domain.entity.saved.ExportTypeModel
import loan.calculator.save.databinding.ItemExportTypeBinding
import loan.calculator.uikit.R
import loan.calculator.uikit.extension.getImageResource

class ExportTypeAdapter(itemList: List<ExportTypeModel>, private val clickListener: ExportItemClick) : BaseAdapter<ExportTypeModel, ExportTypeAdapter.ExportViewHolder>(
    areItemsTheSame = { oldItem, newItem -> oldItem.name == newItem.name && oldItem.name == newItem.name }) {

    init {
        submitList(itemList)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExportViewHolder {
        return ExportViewHolder(
            ItemExportTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


    class ExportItemClick(val clickListener: (model: ExportTypeModel) -> Unit) {
        fun onClick(model: ExportTypeModel) = clickListener(model)
    }

    class ExportViewHolder(private val binding: ItemExportTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: ExportTypeModel) {
            binding.apply {
                title.text = model.name
                logo.setImageResource(model.icon)

                if(model.type==0){
                    status.gone()
                } else {
                    mainLayout.isFocusable = false
                    mainLayout.isEnabled = false
                    mainLayout.isFocusableInTouchMode = false

                    title.setTextColor(ContextCompat.getColor(binding.root.context,R.color.item_background))
                    status.show()
                }
                //title.setTextColor(if(model.type ==0) ContextCompat.getColor(this.root.context, R.color.color_pure_black) else ContextCompat.getColor(this.root.context, R.color.gnt_red))

            }
        }
    }

    override fun onBindViewHolder(holder: ExportViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            clickListener.onClick(getItem(position))
        }
    }
}
