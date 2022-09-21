package com.yalantis.colormatchtabs.colortabs

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.yalantis.colormatchtabs.colortabs.databinding.ItemBinding
import com.yalantis.colormatchtabs.colortabs.model.Menu

/**
 * Created by anna on 29.05.17.
 */
class ListItemAdapter : RecyclerView.Adapter<ListItemAdapter.MusicHolder>() {

    private lateinit var binding: ItemBinding
    var items: List<Menu> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: MusicHolder, position: Int) {
        val menu = items[position]
        holder.layout.apply {
            binding.picture.setImageURI(menu.picture)
            binding.nameOfDish.text = menu.dishName
            binding.restaurantName.text = menu.cafeName
            binding.review.text = menu.reviewAmount
        }
    }

    override fun getItemCount(): Int = items.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicHolder {
        binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicHolder(binding.root)
    }

    class MusicHolder(val layout: LinearLayout) : RecyclerView.ViewHolder(layout)
}