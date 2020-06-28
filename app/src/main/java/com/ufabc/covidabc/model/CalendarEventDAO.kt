package com.ufabc.covidabc.model

import com.google.firebase.firestore.FirebaseFirestore
import java.lang.RuntimeException

object CalendarEventDAO {

    const val EVENT_COLLECTION = "posts"

    fun addEvent(event: CalendarEvent) {
        FirebaseFirestore.getInstance().collection(EVENT_COLLECTION).add(event)
        // TODO: Add onSuccess and onFailure callback

    }
}