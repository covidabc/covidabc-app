package com.ufabc.covidabc.model.logger

import com.google.firebase.firestore.Exclude
import com.google.type.Date
import java.io.Serializable

class NewsViewsLog(uuid: String, ts: java.util.Date, nwsPa: String) : Serializable {
    private var uuid : String = uuid
    private var tStamp: java.util.Date = ts
    private var newsPath: String = nwsPa

    fun getUuid() = uuid
    fun getTStamp() = tStamp
    fun getNewsPath() = newsPath

}
