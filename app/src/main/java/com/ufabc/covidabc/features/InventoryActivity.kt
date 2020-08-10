package com.ufabc.covidabc.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.features.InventoryLocation
import kotlinx.android.synthetic.main.activity_inventory.*

class InventoryActivity : AppCompatActivity() {

    private lateinit var itemRecyclerView: RecyclerView
    private lateinit var locationLocationTextView : TextView

    private lateinit var inventoryLocation : InventoryLocation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory)

        inventoryLocation = intent?.getSerializableExtra(App.INVENTORY_EXTRA) as InventoryLocation

        setViews()
        setInventoryInfo()
    }

    private fun setViews() {
        locationLocationTextView = findViewById(R.id.inventory_location_description_name)
        itemRecyclerView = findViewById(R.id.recycler_view_items)
    }

    private fun setInventoryInfo() {
        locationLocationTextView.text = inventoryLocation.getLocationName()
        populateItemCount(inventoryLocation.getItemCount())
    }

    private fun populateItemCount(itemCount : Map<String, Int>) {
        itemRecyclerView.apply {
            val recyclerView = this
            recyclerView.layoutManager = LinearLayoutManager(App.appContext)
            recyclerView.adapter = ItemCountAdapter(itemCount)
        }
    }
}