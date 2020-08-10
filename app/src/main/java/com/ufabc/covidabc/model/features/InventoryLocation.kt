package com.ufabc.covidabc.model.features

import java.io.Serializable

class InventoryLocation : Serializable {

    lateinit var locationName : String

    // TODO(): counter for all the items
    var count = 0
}