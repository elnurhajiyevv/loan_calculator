package loan.calculator.save.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import loan.calculator.core.base.BaseAdapter
import loan.calculator.domain.entity.saved.GetSavedLoanModel
import loan.calculator.uikit.R

import loan.calculator.uikit.databinding.ItemSavedBinding
import loan.calculator.uikit.extension.getImageBackgroundColor
import loan.calculator.uikit.extension.getImageResource

class SavedAdapter(
    private val clickListener: SavedItemClick
) : BaseAdapter<GetSavedLoanModel, SavedAdapter.SavedViewHolder>(areItemsTheSame = {
        oldItem, newItem -> oldItem.name == newItem.name }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
        return SavedViewHolder(
            ItemSavedBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    class SavedItemClick(val clickListener: (model: GetSavedLoanModel) -> Unit) {
        fun onClick(model: GetSavedLoanModel) = clickListener(model)
    }


    class SavedViewHolder(private val binding: ItemSavedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: GetSavedLoanModel) {
            binding.apply {
                titleText.text = model.name
                startDate.text = model.startDate
                paidOff.text = model.paidOff
                totalRepayment.text = "$ ${model.totalPayment}"
                interestRate.text = "${model.interestRate}%"
                frequency.text = model.compoundingFrequency
                loan.text = "$ ${model.loanAmount}"
                logo.setImageResource(model.type?.getImageResource() ?: R.drawable.bg_balance)
                //logo.background.setTint(model.type?.getImageBackgroundColor(binding.root.context)?:R.color.light_blue_100)
            }
        }
    }

    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            clickListener.onClick(getItem(position))
        }
    }
}
