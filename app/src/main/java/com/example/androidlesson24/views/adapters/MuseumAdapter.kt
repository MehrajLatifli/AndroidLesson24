package com.example.androidlesson24.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlesson24.databinding.ItemMuseumBinding
import com.example.androidlesson24.models.responses.get.museum.MuseumData

class MuseumAdapter : RecyclerView.Adapter<MuseumAdapter.UserViewHolder>() {

    private val list = arrayListOf<MuseumData>()

    lateinit var onClickItem: (MuseumData) -> Unit

    inner class UserViewHolder(val itemBinding: ItemMuseumBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemMuseumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = list[position]
        holder.itemBinding.museum = item

        holder.itemBinding.productMaterialCardView.setOnClickListener {
            onClickItem.invoke(item)
        }
    }

    fun updateList(newList: List<MuseumData>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}
