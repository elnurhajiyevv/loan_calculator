package loan.exchange.setting.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import loan.exchange.core.base.BaseAdapter
import loan.exchange.core.extension.loadFromResource
import loan.exchange.domain.entity.unit.UnitModel
import loan.exchange.setting.databinding.ItemUnitBinding

class UnitAdapter(
    private val itemList: List<UnitModel>,
    private val clickListener: UnitItemClick
) :
    BaseAdapter<UnitModel, UnitAdapter.CurrencyViewHolder>(areItemsTheSame = { oldItem, newItem ->
        oldItem.text == newItem.text && oldItem.icon == newItem.icon
    }) {

    init {
        submitList(itemList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder(
            ItemUnitBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    fun filter(query: CharSequence?) {
        val list = mutableListOf<UnitModel>()
        if (!query.isNullOrEmpty()) {
            list.addAll(
                itemList.filter {
                    it.text.lowercase().contains(query.toString().lowercase())
                })
        } else {
            list.addAll(itemList)
        }
        submitList(list)
    }

    class UnitItemClick(val clickListener: (model: UnitModel) -> Unit) {
        fun onClick(model: UnitModel) = clickListener(model)
    }

    class CurrencyViewHolder(private val binding: ItemUnitBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: UnitModel) {
            binding.apply {
                text.text = model.text
                icon.loadFromResource(model.icon)
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
