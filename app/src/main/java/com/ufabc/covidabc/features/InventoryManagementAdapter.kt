package com.ufabc.covidabc.features

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.features.InventoryLocation

class InventoryManagementAdapter(val inventoryLocationArray: ArrayList<InventoryLocation>) :
    RecyclerView.Adapter<InventoryManagementAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val inventoryLocationText : TextView = itemView.findViewById(R.id.inventory_location_name)
        val inventoryCard : CardView = itemView.findViewById(R.id.inventory_location_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_inventory_locations, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = inventoryLocationArray.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = inventoryLocationArray[position]

        holder.inventoryLocationText.text = item.locationName
        holder.inventoryCard.setOnClickListener {
            Intent(App.appContext, InventoryActivity::class.java).apply {
                putExtra(App.INVENTORY_EXTRA, item)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK;
                ContextCompat.startActivity(App.appContext, this, null)
            }
        }
    }
}