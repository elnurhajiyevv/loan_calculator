package loan.calculator.loan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import loan.calculator.common.extensions.overrideColor
import loan.calculator.core.base.BaseAdapter
import loan.calculator.core.extension.loadFromResource
import loan.calculator.domain.entity.unit.IconModel
import loan.calculator.uikit.databinding.ItemIconBinding
import loan.calculator.uikit.extension.getImageResource

class IconAdapter(private val clickListener: IconItemClick) :
    BaseAdapter<IconModel, IconAdapter.IconViewHolder>(areItemsTheSame = { oldItem, newItem ->
        oldItem.iconResource == newItem.iconResource && oldItem.backgroundColor == newItem.backgroundColor
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        return IconViewHolder(
            ItemIconBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }


    class IconItemClick(val clickListener: (model: IconModel) -> Unit) {
        fun onClick(model: IconModel) = clickListener(model)
    }

    class IconViewHolder(private val binding: ItemIconBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: IconModel) {
            binding.apply {
                iconImageview.setImageResource(model.iconResource.type.getImageResource())
                mainLayout.background.overrideColor(model.backgroundColor)
            }
        }
    }

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            clickListener.onClick(getItem(position))
        }
    }
}
