package loan.calculator.core.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<Item, ViewHolder : RecyclerView.ViewHolder>(
    val areItemsTheSame: ((oldItem: Item, newItem: Item) -> Boolean)? = null,
    val areContentsTheSame: ((oldItem: Item, newItem: Item) -> Boolean)? = null
) : ListAdapter<Item, ViewHolder>(object : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item & Any, newItem: Item & Any): Boolean {
        return areItemsTheSame?.invoke(oldItem, newItem) ?: false
    }

    override fun areContentsTheSame(oldItem: Item & Any, newItem: Item & Any): Boolean {
        return areContentsTheSame?.invoke(oldItem, newItem) ?: false
    }
}) {

}