package com.ufabc.covidabc.model.features

object InventoryLocationDAO {

    fun getAllInventoryLocation() : ArrayList<InventoryLocation> {
        // TODO() : get data from firebase
        val dummyData = InventoryLocation()
        dummyData.locationName = "Dummy name"

        return arrayListOf(dummyData)
    }
}