package com.ufabc.covidabc.model.logger

import java.io.Serializable
import java.util.*

class UserIDLog(uid : String, date : Date, appv : String) : Serializable {
    private var uid : String = uid
    private var date : Date = date
    private var appVersion : String = appv

    fun getUid() = uid
    fun getDate() = date
    fun getAppVersion() = appVersion
}