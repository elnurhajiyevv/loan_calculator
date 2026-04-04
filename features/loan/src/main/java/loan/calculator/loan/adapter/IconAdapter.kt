package loan.calculator.loan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import loan.calculator.core.base.BaseAdapter
import loan.calculator.domain.entity.enum.SELECT_TYPE_LOAN
import loan.calculator.domain.entity.unit.IconModel
import loan.calculator.uikit.databinding.ItemIconBinding
import loan.calculator.uikit.extension.getImageResource

class IconAdapter(
    private val clickListener: IconItemClick) :
    BaseAdapter<IconModel, IconAdapter.IconViewHolder>(
        areItemsTheSame = { oldItem, newItem ->
            oldItem.iconResource == newItem.iconResource
        },
        areContentsTheSame = { oldItem, newItem ->
            oldItem.backgroundColor == newItem.backgroundColor
        }
    ) {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

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
        fun bind(model: IconModel, selected: Boolean) {
            binding.apply {
                iconImageview.setImageResource(model.iconResource.type.getImageResource())
                iconSelectedIndicator.visibility =
                    if (selected) View.VISIBLE else View.GONE
            }
        }
    }

    /** Call after [submitList] commit (or when list is already shown) to highlight the current icon. */
    fun applyInitialSelection(iconResource: SELECT_TYPE_LOAN?) {
        val previous = selectedPosition
        selectedPosition = if (iconResource == null) {
            RecyclerView.NO_POSITION
        } else {
            currentList.indexOfFirst { it.iconResource == iconResource }
        }
        if (previous != RecyclerView.NO_POSITION && previous != selectedPosition) {
            notifyItemChanged(previous)
        }
        if (selectedPosition != RecyclerView.NO_POSITION) {
            notifyItemChanged(selectedPosition)
        }
    }

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        holder.bind(getItem(position), position == selectedPosition)
        holder.itemView.setOnClickListener {
            val pos = holder.adapterPosition
            if (pos == RecyclerView.NO_POSITION) return@setOnClickListener
            val previous = selectedPosition
            selectedPosition = pos
            if (previous != RecyclerView.NO_POSITION && previous != selectedPosition) {
                notifyItemChanged(previous)
            }
            notifyItemChanged(selectedPosition)
            clickListener.onClick(getItem(pos))
        }
    }
}
