package loan.calculator.setting.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import loan.calculator.setting.databinding.ItemColorChoiceBinding
import loan.calculator.uikit.R

class ColorChoiceAdapter(
    private val colors: List<Int>,
    selectedIndex: Int = -1,
    private val onSelected: (index: Int) -> Unit,
) : RecyclerView.Adapter<ColorChoiceAdapter.VH>() {

    var selectedIndex: Int = selectedIndex
        private set

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemColorChoiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun getItemCount() = colors.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(colorRes = colors[position], isSelected = position == selectedIndex)
        holder.itemView.setOnClickListener {
            val prev = selectedIndex
            selectedIndex = position
            if (prev != -1) notifyItemChanged(prev)
            notifyItemChanged(position)
            onSelected(position)
        }
    }

    class VH(private val binding: ItemColorChoiceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(colorRes: Int, isSelected: Boolean) {
            val ctx = binding.root.context
            binding.colorSwatch.setBackgroundColor(ContextCompat.getColor(ctx, colorRes))
            binding.checkColor.visibility = if (isSelected) View.VISIBLE else View.GONE
        }
    }
}

