package com.ufabc.covidabc.model.logger

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

object EventLogDAO {
    private const val USER_LOG_COLLECTION = "event-log"
    fun addUserLog(uLog : NewsViewsLog) {
        FirebaseFirestore.getInstance().collection(USER_LOG_COLLECTION).add(uLog).addOnCompleteListener {
            Log.d("LOG-NEWS-STATUS", it.isSuccessful.toString())
        }
    }
}