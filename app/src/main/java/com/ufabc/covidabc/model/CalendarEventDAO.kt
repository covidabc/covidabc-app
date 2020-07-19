package com.ufabc.covidabc.model

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.lang.RuntimeException

object CalendarEventDAO {

    private const val EVENT_COLLECTION = "eventos"

    fun addEvent(event: CalendarEvent) {
        FirebaseFirestore.getInstance().collection(EVENT_COLLECTION).add(event)
        // TODO: Add onSuccess and onFailure callback
    }

    fun getAllEvents(callback: FirestoreDatabaseOperationListener<ArrayList<CalendarEvent>>) {
        FirebaseFirestore.getInstance().collection(EVENT_COLLECTION).get()
            .addOnSuccessListener { result -> callback.onSuccess(documentsToCalendarEvents(result)) }
            .addOnFailureListener { callback.onFailure() }
    }

    private fun documentsToCalendarEvents(qSnapshot: QuerySnapshot): ArrayList<CalendarEvent> {
        val events = arrayListOf<CalendarEvent>()

        for (document in qSnapshot.documents) {
            document.toObject(CalendarEvent::class.java)?.apply {
                events.add(this)
            }
        }
        return events
    }
}