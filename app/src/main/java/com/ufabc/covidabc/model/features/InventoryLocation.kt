package com.ufabc.covidabc.model.features

import com.google.firebase.firestore.Exclude
import java.io.Serializable

class InventoryLocation(): Serializable {

    private lateinit var locationName: String
    private lateinit var itemCount : HashMap<String, Int>

    private lateinit var refPath: String

    constructor(locationName: String) : this() {
        this.locationName = locationName

        this.itemCount = hashMapOf()
        for (item in DonationItem.values()) {
            itemCount.put(item.toString(), 0)
        }
    }

    fun getLocationName() = this.locationName

    fun getItemCount() = this.itemCount

    @Exclude
    fun getRefPath() : String = this.refPath

    fun setRefPath(refPath: String)  {
        this.refPath = refPath
    }
}