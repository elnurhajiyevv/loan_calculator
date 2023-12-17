package loan.calculator.core.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class SingleRecyclerViewAdapter<T : Any, B : ViewBinding>(
    private val listItems: List<T>,
    val bindingCallback: (ViewGroup?, Boolean) -> B,
    val onBindItemData: B.(data: T) -> Unit,
    val onItemClick: (B.(data: T) -> Unit)? = null,
) : RecyclerView.Adapter<BaseViewHolder>() {

    lateinit var binding: B

    override fun getItemCount(): Int = listItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        binding = bindingCallback.invoke(parent, false)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val item = listItems[position]
        onBindItemData(binding, item)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(binding, item)
        }
    }
}
