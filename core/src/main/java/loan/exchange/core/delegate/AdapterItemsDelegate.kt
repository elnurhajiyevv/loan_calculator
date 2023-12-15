package loan.exchange.core.delegate

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T> adapterItems(
    itemsSame: (T, T) -> Boolean,
    contentSame: (T, T) -> Boolean = { old, new -> old == new },
    init: (() -> List<T>)? = null
) = AdapterItemsDelegate(
    itemsSame,
    contentSame,
    init
)

class AdapterItemsDelegate<T>(
    private val itemsSame: (T, T) -> Boolean,
    private val contentSame: (T, T) -> Boolean = { old, new -> old == new },
    init: (() -> List<T>)? = null
) : ReadWriteProperty<RecyclerView.Adapter<*>, List<T>> {

    private var data: List<T> = init?.invoke() ?: emptyList()

    override fun getValue(thisRef: RecyclerView.Adapter<*>, property: KProperty<*>) = data

    override fun setValue(
        thisRef: RecyclerView.Adapter<*>,
        property: KProperty<*>,
        value: List<T>
    ) {
        val diff = DiffUtil.calculateDiff(
            object : DiffUtil.Callback() {
                override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int) = Any()

                override fun getOldListSize() = data.size

                override fun getNewListSize() = value.size

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    itemsSame(data[oldItemPosition], value[newItemPosition])

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    contentSame(data[oldItemPosition], value[newItemPosition])
            }
        )
        data = value
        diff.dispatchUpdatesTo(thisRef)
    }
}