package com.ufabc.covidabc.model

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.lang.RuntimeException

object CalendarEventDAO {

    private const val EVENT_COLLECTION = "eventos"
    private var isAlreadyFetched = false
    private var eventArray : ArrayList<CalendarEvent> = arrayListOf()

    fun addEvent(event: CalendarEvent) {
        FirebaseFirestore.getInstance().collection(EVENT_COLLECTION).add(event)
        // TODO: Add onSuccess and onFailure callback
    }

    fun refreshFAQ(callback: FirestoreDatabaseOperationListener<Boolean>) {
        FirebaseFirestore.getInstance().collection(this.EVENT_COLLECTION).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    this.eventArray = documentsToCalendarEvents(task.result!!)
                }

                this.isAlreadyFetched = true
                callback.onCompleted(task.isSuccessful)
            }
    }

    fun getEventArray() : ArrayList<CalendarEvent> = this.eventArray

    private fun documentsToCalendarEvents(qSnapshot: QuerySnapshot): ArrayList<CalendarEvent> {
        val events = arrayListOf<CalendarEvent>()

        for (document in qSnapshot.documents) {
            document.toObject(CalendarEvent::class.java)?.apply {
                events.add(this)
            }
        }
        return events
    }

    fun isAlreadyFetched() : Boolean = this.isAlreadyFetched
}