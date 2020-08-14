package com.ufabc.covidabc.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.FirestoreDatabaseOperationListener
import com.ufabc.covidabc.model.features.InventoryLocation
import com.ufabc.covidabc.model.features.InventoryLocationDAO
import java.util.*

class InventoryActivity : AppCompatActivity() {

    private lateinit var itemRecyclerView: RecyclerView
    private lateinit var locationLocationTextView : TextView
    private lateinit var inventorySearchView: SearchView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var toolbar: Toolbar

    private lateinit var inventoryLocation : InventoryLocation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory)

        inventoryLocation = intent?.getSerializableExtra(App.INVENTORY_EXTRA) as InventoryLocation

        setViews()
        setListeners()
        setInventoryInfo()
    }

    private fun setViews() {
        locationLocationTextView = findViewById(R.id.inventory_location_description_name)
        itemRecyclerView = findViewById(R.id.recycler_view_items)
        inventorySearchView = findViewById(R.id.search_view_inventory)
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout_inventory)
        toolbar = findViewById(R.id.item_location_toolbar)
    }

    private fun setListeners() {
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_delete_location -> confirmDeleteLocationDialog()
                else -> super.onOptionsItemSelected(it)
            }

            true
        }

        inventorySearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            private var timer = Timer()

            override fun onQueryTextSubmit(str: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(str: String?): Boolean {
                timer.cancel()
                swipeRefreshLayout.isRefreshing = true

                timer = Timer()
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        ContextCompat.getMainExecutor(App.appContext)
                            .execute {
                                populateItemCount(filterItemCount(str.orEmpty()))
                                swipeRefreshLayout.isRefreshing = false
                            }
                    }
                }, 600)

                return false
            }
        })
    }

    private fun confirmDeleteLocationDialog() {
        val builder = AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.text_aviso))
            setMessage(getString(R.string.confirm_location_delete))
            setPositiveButton(getString(R.string.confirm_sim) ){ dialog, which ->
                deleteLocation()
                dialog.dismiss()
            }
            setNegativeButton(getString(R.string.confirm_não)){ dialog, wich ->
                dialog.dismiss()
            }
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun deleteLocation() {
        InventoryLocationDAO.deleteLocation(this.inventoryLocation, object: FirestoreDatabaseOperationListener<Boolean> {
            override fun onCompleted(sucess: Boolean) {
                if (sucess) {
                    Toast.makeText(App.appContext, R.string.delete_sucess, Toast.LENGTH_LONG).show()
                    finish()
                }
                else {
                    Toast.makeText(App.appContext, R.string.delete_failure, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun filterItemCount(filter : String) : Map<String, Int> =
        this.inventoryLocation.getItemCount().filter { it.key.contains(filter, ignoreCase = true) }

    private fun setInventoryInfo() {
        locationLocationTextView.text = getString(R.string.welcome_to, inventoryLocation.getLocationName())
        populateItemCount(inventoryLocation.getItemCount())
    }

    private fun populateItemCount(itemCount : Map<String, Int>) {
        itemRecyclerView.apply {
            val recyclerView = this
            recyclerView.layoutManager = LinearLayoutManager(App.appContext)
            recyclerView.adapter = ItemCountAdapter(itemCount,
                this@InventoryActivity, inventoryLocation.getRefPath())
        }
    }

    fun refreshInventory() {
        InventoryLocationDAO.refreshInventoryLocation(object: FirestoreDatabaseOperationListener<Boolean> {
            override fun onCompleted(sucess: Boolean) {
                inventoryLocation = InventoryLocationDAO.getIventoryLocationWithRef(inventoryLocation.getRefPath())
                populateItemCount(filterItemCount(inventorySearchView.query.toString()))
            }
        })
    }
}