package com.ufabc.covidabc.features

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.features.InventoryLocation

class InventoryManagementAdapter(val inventoryLocationArray: ArrayList<InventoryLocation>) :
    BaseAdapter() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val inventoryLocationText : TextView = itemView.findViewById(R.id.inventory_location_name)
        val inventoryCard : CardView = itemView.findViewById(R.id.inventory_location_card)
    }

    override fun getCount(): Int = inventoryLocationArray.size

    private fun bindViewHolder(holder: ViewHolder, position: Int) {
        val item = inventoryLocationArray[position]

        holder.inventoryLocationText.text = item.getLocationName()
        holder.inventoryCard.setOnClickListener {
            Intent(App.appContext, InventoryActivity::class.java).apply {
                putExtra(App.INVENTORY_EXTRA, item)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK;
                ContextCompat.startActivity(App.appContext, this, null)
            }
        }
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View? {
        val viewHolder: ViewHolder
        val rowView: View?

        if (view == null) {
            rowView = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.cardview_inventory_locations, parent, false)

            viewHolder = ViewHolder(rowView)
            rowView.tag = viewHolder
        } else {
            rowView = view
            viewHolder = rowView.tag as ViewHolder
        }

        bindViewHolder(viewHolder, position)

        return rowView
    }

    override fun getItem(position: Int) = inventoryLocationArray[position]

    override fun getItemId(position: Int): Long  = 0

}