package com.ufabc.covidabc.model.features

import java.io.Serializable

class InventoryLocation(): Serializable {
    private lateinit var locationName: String

    private lateinit var itemCount : HashMap<String, Int>

    fun getLocationName() = this.locationName

    fun getItemCount() = this.itemCount
}