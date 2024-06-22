package com.example.turkishmuseums.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.turkishmuseums.R
import com.example.turkishmuseums.databinding.ItemDistrictBinding
import com.example.turkishmuseums.models.responses.get.region.DistrictData

class DistrictAdapter : RecyclerView.Adapter<DistrictAdapter.RegionViewHolder>() {

    private val list = mutableListOf<DistrictData>()
    private var lastSelectedItemPosition = RecyclerView.NO_POSITION

    lateinit var onClickItem: (String) -> Unit

    inner class RegionViewHolder(val itemBinding: ItemDistrictBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder {
        val binding = ItemDistrictBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RegionViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RegionViewHolder, position: Int) {
        val item = list[position]
        holder.itemBinding.district = item

        val isSelected = position == lastSelectedItemPosition

        holder.itemBinding.constraintLayout.setBackgroundColor(
            ContextCompat.getColor(
                holder.itemView.context,
                if (isSelected) R.color.white else R.color.brown
            )
        )
        holder.itemBinding.textView7.setTextColor(
            ContextCompat.getColor(
                holder.itemView.context,
                if (isSelected) R.color.brown else R.color.white
            )
        )


        holder.itemView.setOnClickListener {
            val currentSelectedItemPosition = holder.bindingAdapterPosition

            if (currentSelectedItemPosition != lastSelectedItemPosition) {
                val previousSelectedItemPosition = lastSelectedItemPosition
                lastSelectedItemPosition = currentSelectedItemPosition
                notifyItemChanged(previousSelectedItemPosition)
                notifyItemChanged(currentSelectedItemPosition)
                onClickItem.invoke(list[currentSelectedItemPosition].cities.lowercase())
            } else {
                lastSelectedItemPosition = RecyclerView.NO_POSITION
                notifyItemChanged(currentSelectedItemPosition)
                onClickItem.invoke("")
            }
        }
    }


    fun updateList(newList: List<DistrictData>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun resetSelectedItemPosition() {
        lastSelectedItemPosition = RecyclerView.NO_POSITION
        notifyDataSetChanged()
    }
}