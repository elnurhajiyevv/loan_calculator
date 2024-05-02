package loan.calculator.loan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import loan.calculator.common.extensions.getMonthAndYear
import loan.calculator.core.base.BaseAdapter
import loan.calculator.domain.entity.home.AmortizationModel
import loan.calculator.loan.R

import loan.calculator.uikit.textview.AutofitTextView
import java.text.NumberFormat
import java.util.Locale
class AmortizationAdapter(
    private val listItems: ArrayList<AmortizationModel?>,
    private val onModelClick: AmortizationModelClick,
): BaseAdapter<AmortizationModel, RecyclerView.ViewHolder>(areContentsTheSame = {
        oldItem, newItem -> oldItem.beginningBalance == newItem.beginningBalance }) {

    var nf: NumberFormat = NumberFormat.getCurrencyInstance(Locale.US)
    init {
        submitList(listItems)
    }

    inner class AmortizationTypeViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var mainContainer: ConstraintLayout
        var number: AutofitTextView
        var period: AutofitTextView
        var interest: AutofitTextView
        var principal: AutofitTextView
        var balance: AutofitTextView
        init {
            mainContainer = view.findViewById<View>(R.id.main_container) as ConstraintLayout
            number = view.findViewById<View>(R.id.number) as AutofitTextView
            period = view.findViewById<View>(R.id.period) as AutofitTextView
            interest = view.findViewById<View>(R.id.interest) as AutofitTextView
            principal = view.findViewById<View>(R.id.principal) as AutofitTextView
            balance = view.findViewById<View>(R.id.balance) as AutofitTextView
        }

    }

    inner class AmortizationEndOfYearTypeViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var year: AutofitTextView
        var mainContainer: ConstraintLayout
        init {
            mainContainer = view.findViewById<View>(R.id.main_container) as ConstraintLayout
            year = view.findViewById<View>(R.id.year) as AutofitTextView
        }
    }

    class AmortizationModelClick(val clickListener: (model: AmortizationModel) -> Unit){
        fun onClick(model: AmortizationModel) = clickListener(model)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        when(viewType){
            0 -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_amortization,parent,false)
                return AmortizationTypeViewHolder(view)
            }
            1 -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_end_of_year,parent,false)
                return AmortizationEndOfYearTypeViewHolder(view)
            }
            else -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_amortization,parent,false)
                return AmortizationTypeViewHolder(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(listItems[position]?.type){
            0 -> 0
            1 -> 1
            else -> -1
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = listItems[position]
        when(model?.type){
            0 ->{
                (holder as AmortizationTypeViewHolder).mainContainer.setBackgroundColor(ContextCompat.getColor(holder.mainContainer.rootView.context, if(model.numberOfItems % 2 == 0) R.color.adapter_amortization else R.color.color_white_gray))
                holder.number.text = model.month.toString()
                holder.period.text = model.month.toString().getMonthAndYear()
                holder.interest.text = nf.format(model.interest)
                holder.principal.text = nf.format(model.principal)
                holder.balance.text = nf.format(model.endingBalance)
                holder.itemView.setOnClickListener {
                    onModelClick.onClick(model)
                }
            }
            1 ->{
                (holder as AmortizationEndOfYearTypeViewHolder).mainContainer.setBackgroundColor(ContextCompat.getColor(holder.mainContainer.rootView.context, if(model.numberOfItems % 2 == 0) R.color.adapter_amortization else R.color.color_white_gray))
                holder.year.text = "End of Year " + model.countOfYear
            }
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }
}

