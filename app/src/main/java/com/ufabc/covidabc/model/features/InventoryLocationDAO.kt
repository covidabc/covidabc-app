package com.ufabc.covidabc.model.features

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.ufabc.covidabc.model.FirestoreDatabaseOperationListener
import kotlin.math.min

object InventoryLocationDAO {
    private const val INVENTORY_COLLECTION = "inventory-locations"

    private lateinit var inventoryLocationArray : ArrayList<InventoryLocation>
    private lateinit var totalCount : HashMap<String, Int>
    private var isAlreadyFetched = false
    private var kitCount = 0

    fun addNewInventoryLocation(locationName: String) {
        val newLocation = InventoryLocation(locationName)

        FirebaseFirestore.getInstance().collection(INVENTORY_COLLECTION).add(newLocation)
    }

    fun refreshInventoryLocation(callback: FirestoreDatabaseOperationListener<Boolean>) {
        FirebaseFirestore.getInstance().collection(INVENTORY_COLLECTION).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    this.inventoryLocationArray = documentsToInventoryLocation(task.result!!)
                    this.isAlreadyFetched = true
                    refreshTotalCount()

                    Log.e("kits", kitCount.toString())
                }

                callback.onCompleted(task.isSuccessful)
            }
    }

    fun deleteLocation(location: InventoryLocation, callback: FirestoreDatabaseOperationListener<Boolean>) {
        val refPath = location.getRefPath()

        FirebaseFirestore.getInstance()
            .document(refPath)
            .delete()
            .addOnCompleteListener { task->
                callback.onCompleted(task.isSuccessful)
            }
    }

    fun getInventoryLocationArray() = this.inventoryLocationArray

    fun getKitCount() = this.kitCount

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

    private fun refreshTotalCount() {
        totalCount = hashMapOf()
        var minCount = Int.MAX_VALUE

        for (item in DonationItem.values()) {
            val itemMinCount = item.getMinCount()
            val itemName = item.toString()

            val itemCount : Int = inventoryLocationArray.fold (
                0, { acc, next -> acc + (next.getItemCount()[itemName] ?: 0) }
            )

            totalCount[itemName] = (totalCount[itemName] ?: 0) + itemCount

            if (itemMinCount > 0) {
                minCount = min(minCount, itemCount/itemMinCount)
            }
        }

        this.kitCount = minCount
    }
}