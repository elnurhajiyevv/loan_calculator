package loan.calculator.loan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import loan.calculator.common.extensions.getMonthAndYear
import loan.calculator.core.base.BaseAdapter
import loan.calculator.domain.entity.home.AmortizationModel
import loan.calculator.loan.R

import loan.calculator.uikit.databinding.ItemAmortizationBinding
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

class AmortizationAdapter : BaseAdapter<AmortizationModel, AmortizationAdapter.AmortizationViewHolder>(areItemsTheSame = { oldItem, newItem ->
        oldItem.beginningBalance == newItem.beginningBalance && oldItem.endingBalance == newItem.endingBalance
    }) {

    var nf: NumberFormat = NumberFormat.getCurrencyInstance(Locale.US)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmortizationViewHolder {
        return AmortizationViewHolder(
            ItemAmortizationBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    class AmortizationViewHolder(private val binding: ItemAmortizationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(numberFormat: NumberFormat,model: AmortizationModel) {
            binding.apply {
                mainContainer.setBackgroundColor(ContextCompat.getColor(binding.root.context, if(model.month % 2 == 0) R.color.adapter_background else R.color.color_white_gray))
                number.text = model.month.toString()
                period.text = model.month.toString().getMonthAndYear()
                interest.text = numberFormat.format(model.interest)
                principal.text = numberFormat.format(model.principal)
                balance.text = numberFormat.format(model.endingBalance)
            }
        }
    }

    override fun onBindViewHolder(holder: AmortizationViewHolder, position: Int) {
        holder.bind(nf,getItem(position))
    }
}
