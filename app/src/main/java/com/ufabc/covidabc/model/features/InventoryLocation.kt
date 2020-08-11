package com.ufabc.covidabc.model.features

import java.io.Serializable

class InventoryLocation(): Serializable {
    enum class EventType(private val value: String) {
        DONATION("Doação"),
        DEMO("Manifestação"),
        COLLECTION("Arrecadação");

        override fun toString() = value
    }
    private lateinit var locationName: String

    private lateinit var itemCount : HashMap<String, Int>

    constructor(locationName: String) : this() {
        this.locationName = locationName

        this.itemCount = hashMapOf()
        for (item in DonationItem.values()) {
            itemCount.put(item.toString(), 0)
        }
    }

    fun getLocationName() = this.locationName

    fun getItemCount() = this.itemCount
}