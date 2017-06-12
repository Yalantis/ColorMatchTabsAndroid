package com.yalantis.colormatchtabs.colortabs

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.yalantis.colormatchtabs.colortabs.model.Menu
import kotlinx.android.synthetic.main.item.view.*

/**
 * Created by anna on 29.05.17.
 */
class ListItemAdapter() : RecyclerView.Adapter<ListItemAdapter.MusicHolder>() {

    var items: List<Menu> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: MusicHolder?, position: Int) {
        val menu = items[position]
        holder?.layout?.apply {
            picture.setImageURI(menu.picture)
            nameOfDish.text = menu.dishName
            restaurantName.text = menu.cafeName
            review.text = menu.reviewAmount
        }
    }

    override fun getItemCount(): Int = items.count()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MusicHolder {
        val layout = LayoutInflater.from(parent?.context).inflate(R.layout.item, parent, false) as LinearLayout
        return MusicHolder(layout)
    }

    class MusicHolder(val layout: LinearLayout) : RecyclerView.ViewHolder(layout)
}