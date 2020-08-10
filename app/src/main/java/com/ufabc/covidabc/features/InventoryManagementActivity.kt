package com.ufabc.covidabc.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.features.InventoryLocation
import com.ufabc.covidabc.model.features.InventoryLocationDAO

class InventoryManagementActivity : AppCompatActivity() {

    private lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory_management)

        setViews()
        populateInventoryLocations(InventoryLocationDAO.getAllInventoryLocation())
    }

    private fun setViews() {
        recyclerView = findViewById(R.id.recycler_view_inventory_management)
    }

    private fun populateInventoryLocations(inventoryLocationArray : ArrayList<InventoryLocation>) {
        recyclerView.apply {
            val recyclerView = this
            recyclerView.layoutManager = LinearLayoutManager(App.appContext)
            recyclerView.adapter = InventoryManagementAdapter(inventoryLocationArray)
        }
    }
}