package com.ufabc.covidabc.features

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.FirestoreDatabaseOperationListener
import com.ufabc.covidabc.model.features.InventoryLocation
import com.ufabc.covidabc.model.features.InventoryLocationDAO
import kotlinx.android.synthetic.main.dialog_create_location.*
import kotlinx.android.synthetic.main.dialog_quit.*

class InventoryManagementActivity : AppCompatActivity() {

    private lateinit var recyclerView : ListView
    private lateinit var createLocationButton : Button

    private var shouldUpdate: Boolean = false

    override fun onPause() {
        super.onPause()

        shouldUpdate = true
    }

    override fun onResume() {
        super.onResume()

        if (shouldUpdate) {
            shouldUpdate = false
            updateData()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory_management)

        setViews()
        setListeners()
        updateData()
    }

    private fun updateData() {
        InventoryLocationDAO.refreshInventoryLocation(object : FirestoreDatabaseOperationListener<Boolean> {
            override fun onCompleted(sucess: Boolean) {
                val updatedInv = InventoryLocationDAO.getInventoryLocationArray()
                populateInventoryLocations(updatedInv)
            }
        })
    }

    private fun setViews() {
        recyclerView = findViewById(R.id.recycler_view_inventory_management)
        createLocationButton = findViewById(R.id.create_location_button)
    }

    private fun setListeners() {
        createLocationButton.setOnClickListener {
            showCreateLocationDialog()
        }
    }

    private fun showCreateLocationDialog() {
        Dialog(this).apply {
            setCancelable(false)
            setContentView(R.layout.dialog_create_location)

            val locationNameEditText: EditText = findViewById(R.id.create_location_edit_text)

            quit_create_location_button.setOnClickListener {
                dismiss()
            }

            continue_create_location_button.setOnClickListener {
                val locationName = locationNameEditText.text.toString()

                if (locationName.isNotEmpty()) {
                    InventoryLocationDAO.addNewInventoryLocation(locationName)
                    updateData()
                    dismiss()
                }
                else {
                    locationNameEditText.error = getString(R.string.fill_in_fields)
                }
            }
        }.show()
    }


    private fun populateInventoryLocations(inventoryLocationArray : ArrayList<InventoryLocation>) {
        recyclerView.apply {
            val recyclerView = this
            recyclerView.adapter = InventoryManagementAdapter(inventoryLocationArray)
        }
    }
}