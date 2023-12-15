package loan.exchange.uikit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import loan.exchange.core.base.BaseAdapter
import loan.exchange.domain.entity.home.CurrencyModel
import loan.exchange.uikit.databinding.ItemCurrencyBinding
import loan.exchange.uikit.extension.getImageResource

class CurrencyAdapter(
    private val itemList: List<CurrencyModel>,
    private val clickListener: CurrencyItemClick
) :
    BaseAdapter<CurrencyModel, CurrencyAdapter.CurrencyViewHolder>(areItemsTheSame = { oldItem, newItem ->
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
        val list = mutableListOf<CurrencyModel>()
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

    class CurrencyItemClick(val clickListener: (model: CurrencyModel) -> Unit) {
        fun onClick(model: CurrencyModel) = clickListener(model)
    }

    class CurrencyViewHolder(private val binding: ItemCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: CurrencyModel) {
            binding.apply {
                currency.setImageResource(model.name.getImageResource())
                currencyName.text = model.name
                currencyDescription.text = model.description
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
