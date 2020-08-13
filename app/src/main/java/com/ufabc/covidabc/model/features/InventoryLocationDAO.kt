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

    fun getIventoryLocationWithRef(refPath : String) : InventoryLocation =
        inventoryLocationArray.filter { inventoryLocation -> inventoryLocation.getRefPath() == refPath }.first()

    fun updateItemCount(refPath : String, item : String, currVal : Int, change: Int,
                        callback: FirestoreDatabaseOperationListener<Boolean>) {
        FirebaseFirestore.getInstance().document(refPath).get()
            .addOnCompleteListener { getTask ->
                if (!getTask.isSuccessful) {
                    callback.onCompleted(false)
                    return@addOnCompleteListener
                }

                val querySnapshot = getTask.result!!
                val map = querySnapshot.toObject(InventoryLocation::class.java)!!.getItemCount()

                if (map[item] != currVal) {
                    callback.onCompleted(false)
                    return@addOnCompleteListener
                }

                map[item] = currVal + change
                FirebaseFirestore.getInstance().document(refPath)
                    .update(mapOf("itemCount" to map))
                    .addOnCompleteListener {
                            updateTask -> callback.onCompleted(updateTask.isSuccessful)
                    }
            }
    }

    private fun documentsToInventoryLocation(qSnapshot: QuerySnapshot): ArrayList<InventoryLocation> {
        val inventoryLocationArray = arrayListOf<InventoryLocation>()

        for (document in qSnapshot.documents) {
            document.toObject(InventoryLocation::class.java)?.apply {
                this.setRefPath(document.reference.path)
                inventoryLocationArray.add(this)
            }
        }

        return inventoryLocationArray
    }
}