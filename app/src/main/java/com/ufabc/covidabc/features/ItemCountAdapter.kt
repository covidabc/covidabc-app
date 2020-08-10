package com.ufabc.covidabc.features

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R

class ItemCountAdapter(private val itemCount: Map<String, Int>) : RecyclerView.Adapter<ItemCountAdapter.ViewHolder>() {

    private var keyMap : List<String> = itemCount.keys.toList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNameTextView : TextView = itemView.findViewById(R.id.item_name_text_view)
        val itemCountTextView : TextView = itemView.findViewById(R.id.item_count_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_item_count, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() : Int = itemCount.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = keyMap[position]
        val count = itemCount[item]

        holder.itemNameTextView.text = item
        holder.itemCountTextView.text = count.toString()
    }
}