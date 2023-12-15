package loan.exchange.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import loan.exchange.core.base.BaseAdapter
import loan.exchange.domain.entity.home.CurrencyModel
import loan.exchange.home.databinding.ItemConvertedCurrencyBinding
import loan.exchange.uikit.extension.getCurrencySymbolResource
import loan.exchange.uikit.extension.getImageResource

class CurrencyConvertedAdapter(
) :
    BaseAdapter<CurrencyModel, CurrencyConvertedAdapter.CurrencyViewHolder>(areItemsTheSame = {
            oldItem, newItem -> oldItem.name == newItem.name
    }) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder(
            ItemConvertedCurrencyBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }


    class CurrencyViewHolder(private val binding: ItemConvertedCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: CurrencyModel) {
            binding.apply {
                currency.setImageResource(model.name.getImageResource())
                currencyName.text = model.name
                currencyDescription.text = model.name.getCurrencySymbolResource()
                convertedAmount.text = model.description
            }
        }
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
