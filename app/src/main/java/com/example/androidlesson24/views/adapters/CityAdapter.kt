package com.example.androidlesson24.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlesson24.R
import com.example.androidlesson24.databinding.ItemCityBinding
import com.example.androidlesson24.models.responses.get.city.CityData


class CityAdapter : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    private val list = mutableListOf<CityData>()
    private var lastSelectedItemPosition = RecyclerView.NO_POSITION

          lateinit var onClickItem: (String) -> Unit

    inner class CityViewHolder(val itemBinding: ItemCityBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val item = list[position]
        holder.itemBinding.cities = item

        val isSelected = position == lastSelectedItemPosition

        holder.itemBinding.constraintLayout.setBackgroundColor(
            ContextCompat.getColor(
                holder.itemView.context,
                if (isSelected) R.color.white else R.color.burntsienna
            )
        )
        holder.itemBinding.textView7.setTextColor(
            ContextCompat.getColor(
                holder.itemView.context,
                if (isSelected) R.color.burntsienna else R.color.white
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


    fun updateList(newList: List<CityData>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun resetSelectedItemPosition() {
        lastSelectedItemPosition = RecyclerView.NO_POSITION
        notifyDataSetChanged()
    }
}