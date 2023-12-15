package loan.exchange.setting.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import loan.exchange.core.base.BaseAdapter

import loan.exchange.setting.databinding.ItemUnitDetailBinding
import loan.exchange.setting.util.UnitConverter


class UnitDetailAdapter(
    private val clickListener: UnitItemClick
) :
    BaseAdapter<UnitConverter.Unit, UnitDetailAdapter.CurrencyViewHolder>(areItemsTheSame = { oldItem, newItem ->
        oldItem.names == newItem.names
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder(
            ItemUnitDetailBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    class UnitItemClick(val clickListener: (model: UnitConverter.Unit) -> Unit) {
        fun onClick(model: UnitConverter.Unit) = clickListener(model)
    }

    class CurrencyViewHolder(private val binding: ItemUnitDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: UnitConverter.Unit) {
            binding.apply {
                textViewTitle.text = model.names
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
