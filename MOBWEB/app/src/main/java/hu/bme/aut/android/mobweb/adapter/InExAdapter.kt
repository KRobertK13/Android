package hu.bme.aut.android.mobweb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.mobweb.R
import hu.bme.aut.android.mobweb.data.InExItem
import hu.bme.aut.android.mobweb.databinding.ItemInexListBinding

class InExAdapter(private val listener: InExItemClickListener) :
    RecyclerView.Adapter<InExAdapter.InExItemViewHolder>() {

    private val items = mutableListOf<InExItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = InExItemViewHolder(
        ItemInexListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: InExItemViewHolder, position: Int) {
        val inExItem = items[position]

        holder.binding.ivIcon.setImageResource(getImageResource(inExItem.category))

        holder.binding.tvName.text = inExItem.name
        holder.binding.tvDescription.text = inExItem.description
        holder.binding.tvCategory.text = inExItem.category.name
        var s =""
        if(!inExItem.sign) s = s.plus("-")
        holder.binding.tvPrice.text = s.plus( "${inExItem.amount} Ft")
        holder.binding.ibRemove.setOnClickListener{
            listener.onItemRemoved(inExItem)
        }
    }

    @DrawableRes()
    private fun getImageResource(category: InExItem.Category): Int {
        return when (category) {
            InExItem.Category.WORK -> R.drawable.work
            InExItem.Category.PURCHASE-> R.drawable.buy
            InExItem.Category.SALE -> R.drawable.sell
            InExItem.Category.ENTERTAINMENT -> R.drawable.entertainment
            InExItem.Category.OVERHEAD -> R.drawable.overhead
        }
    }

    override fun getItemCount(): Int = items.size

    interface InExItemClickListener {
        fun onItemRemoved(item: InExItem)
    }
    fun addItem(item: InExItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }
    fun removeItem(item: InExItem){
        val i = items.indexOf(item)
        items.remove(item)
        notifyItemRemoved(i)
    }

    fun update(inExItems: List<InExItem>) {
        items.clear()
        items.addAll(inExItems)
        notifyDataSetChanged()
    }
    inner class InExItemViewHolder(val binding: ItemInexListBinding) : RecyclerView.ViewHolder(binding.root)
}