package com.ufabc.covidabc.model.logger

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

object EventLogDAO {
    private const val USER_LOG_COLLECTION = "eventos-log"
    fun addEventLog(eLog : EventLog) {
        FirebaseFirestore.getInstance().collection(USER_LOG_COLLECTION).add(eLog).addOnCompleteListener {
            Log.d("LOG-Event-STATUS", it.isSuccessful.toString())
        }
    }
}