package loan.calculator.save.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import loan.calculator.core.base.BaseAdapter
import loan.calculator.domain.entity.home.SavedModel
import loan.calculator.save.databinding.ItemSavedBinding

class SavedAdapter(
    private val clickListener: SavedItemClick
) : BaseAdapter<SavedModel, SavedAdapter.CurrencyViewHolder>(areItemsTheSame = {
        oldItem, newItem -> oldItem.name == newItem.name }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder(
            ItemSavedBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    class SavedItemClick(val clickListener: (model: SavedModel) -> Unit) {
        fun onClick(model: SavedModel) = clickListener(model)
    }


    class CurrencyViewHolder(private val binding: ItemSavedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: SavedModel) {
            binding.apply {
                titleText.text = model.name
                startDateText.text = model.name
            }
        }
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            clickListener.onClick(getItem(position))
        }
    }
}
