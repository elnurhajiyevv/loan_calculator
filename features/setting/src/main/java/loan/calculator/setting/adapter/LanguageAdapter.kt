package loan.calculator.setting.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import loan.calculator.core.base.BaseAdapter
import loan.calculator.domain.entity.home.LanguageModel
import loan.calculator.setting.R
import loan.calculator.setting.databinding.ItemLanguageBinding
import loan.calculator.uikit.extension.getImageResource

class LanguageAdapter(private val itemList: List<LanguageModel>, private val clickListener: LanguageItemClick) : BaseAdapter<LanguageModel, LanguageAdapter.LanguageViewHolder>(
    areItemsTheSame = { oldItem, newItem -> oldItem.name == newItem.name && oldItem.nationalName == newItem.nationalName }) {

    init {
        submitList(itemList)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        return LanguageViewHolder(
            ItemLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    fun filter(query: CharSequence?) {
        val list = mutableListOf<LanguageModel>()
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

    class LanguageItemClick(val clickListener: (model: LanguageModel) -> Unit) {
        fun onClick(model: LanguageModel) = clickListener(model)
    }

    class LanguageViewHolder(private val binding: ItemLanguageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: LanguageModel) {
            binding.apply {
                binding.logo.setImageResource(model.name.getImageResource())
                binding.title.text = model.name
                binding.description.text = model.nationalName
                if(model.isSelected){
                    binding.mainLayout.setBackgroundColor(ContextCompat.getColor(binding.root.context,R.color.lang_background))
                }
            }
        }
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            clickListener.onClick(getItem(position))
        }
    }
}
