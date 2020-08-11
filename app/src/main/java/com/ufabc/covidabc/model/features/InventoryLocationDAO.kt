package com.ufabc.covidabc.model.features

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.ufabc.covidabc.model.FirestoreDatabaseOperationListener
import com.ufabc.covidabc.model.event.CalendarEvent
import com.ufabc.covidabc.model.event.CalendarEventDAO

object InventoryLocationDAO {
    private const val INVENTORY_COLLECTION = "inventory-locations"

    private lateinit var inventoryLocationArray : ArrayList<InventoryLocation>
    private var isAlreadyFetched = false

    fun addNewInventoryLocation(locationName: String) {
        val newLocation = InventoryLocation(locationName)

        FirebaseFirestore.getInstance().collection(INVENTORY_COLLECTION).add(newLocation)
    }

    fun refreshInventoryLocation(callback: FirestoreDatabaseOperationListener<Boolean>) {
        FirebaseFirestore.getInstance().collection(INVENTORY_COLLECTION).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    this.inventoryLocationArray =
                        documentsToInventoryLocation(task.result!!)
                    this.isAlreadyFetched = true
                }

                callback.onCompleted(task.isSuccessful)
            }
    }

    fun getInventoryLocationArray() = this.inventoryLocationArray

    private fun documentsToInventoryLocation(qSnapshot: QuerySnapshot): ArrayList<InventoryLocation> {
        val inventoryLocationArray = arrayListOf<InventoryLocation>()

        for (document in qSnapshot.documents) {
            document.toObject(InventoryLocation::class.java)?.apply {
                inventoryLocationArray.add(this)
            }
        }

        return inventoryLocationArray
    }
}