package loan.calculator.loan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import loan.calculator.core.base.BaseAdapter
import loan.calculator.domain.entity.home.SavedModel
import loan.calculator.loan.databinding.ItemCurrencyBinding

class CurrencyAdapter(
    private val itemList: List<SavedModel>,
    private val clickListener: CurrencyItemClick
) :
    BaseAdapter<SavedModel, CurrencyAdapter.CurrencyViewHolder>(areItemsTheSame = { oldItem, newItem ->
        oldItem.name == newItem.name
    }) {

    init {
        submitList(itemList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder(
            ItemCurrencyBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    fun filter(query: CharSequence?) {
        val list = mutableListOf<SavedModel>()
        if (!query.isNullOrEmpty()) {
            list.addAll(
                itemList.filter {
                    it.name?.lowercase()?.contains(query.toString().lowercase()) == true
            })
        } else {
            list.addAll(itemList)
        }
        submitList(list)
    }

    class CurrencyItemClick(val clickListener: (model: SavedModel) -> Unit) {
        fun onClick(model: SavedModel) = clickListener(model)
    }

    class CurrencyViewHolder(private val binding: ItemCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: SavedModel) {
            binding.apply {

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
