package com.ufabc.covidabc.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.features.InventoryLocation

class InventoryActivity : AppCompatActivity() {

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
    }

    private fun setInventoryInfo() {
        locationLocationTextView.text = inventoryLocation.locationName
    }
}