package com.ufabc.covidabc.model.event

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.ufabc.covidabc.model.FirestoreDatabaseOperationListener
import java.util.*
import kotlin.collections.ArrayList

object CalendarEventDAO {

    private const val EVENT_COLLECTION = "eventos"
    private var isAlreadyFetched = false
    private var eventArray : ArrayList<CalendarEvent> = arrayListOf()

    fun addEvent(event: CalendarEvent, callback: FirestoreDatabaseOperationListener<Boolean>) {
        FirebaseFirestore.getInstance().collection(EVENT_COLLECTION).add(event)
            .addOnCompleteListener { task ->
                callback.onCompleted(task.isSuccessful)
            }
    }

    fun editEvent(event: CalendarEvent, callback: FirestoreDatabaseOperationListener<Boolean>) {
        val refPath = event.getRefPath()

        FirebaseFirestore.getInstance().document(refPath)
            .update(calendarEventToDocument(event))
            .addOnCompleteListener { task->
                callback.onCompleted(task.isSuccessful)
            }
    }

    fun deleteEvent(event: CalendarEvent, callback: FirestoreDatabaseOperationListener<Boolean>) {
        val refPath = event.getRefPath()

        FirebaseFirestore.getInstance()
            .document(refPath)
            .delete()
            .addOnCompleteListener { task->
                callback.onCompleted(task.isSuccessful)
            }
    }

    fun refreshFAQ(callback: FirestoreDatabaseOperationListener<Boolean>) {
        val yesterday = Calendar.getInstance()
        yesterday.add(Calendar.DAY_OF_MONTH, -1);

        FirebaseFirestore.getInstance().collection(EVENT_COLLECTION)
            .orderBy("date")
            .whereGreaterThanOrEqualTo("date", yesterday.time)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    eventArray =
                        documentsToCalendarEvents(
                            task.result!!
                        )
                }

                isAlreadyFetched = true
                callback.onCompleted(task.isSuccessful)
            }
    }

    fun getEventArray() : ArrayList<CalendarEvent> = eventArray

    private fun documentsToCalendarEvents(qSnapshot: QuerySnapshot): ArrayList<CalendarEvent> {
        val events = arrayListOf<CalendarEvent>()

        for (document in qSnapshot.documents) {
            document.toObject(CalendarEvent::class.java)?.apply {
                this.setRefPath(document.reference.path)
                events.add(this)
            }
        }
        return events
    }

    private fun calendarEventToDocument(event: CalendarEvent) : Map<String, Any> {
        return mapOf(
            "date" to event.getDate(),
            "title" to event.getTitle(),
            "description" to event.getDescription(),
            "eventType" to event.getEventType(),
            "latitute" to event.getLatitude(),
            "longitude" to event.getLongitude(),
            "place" to event.getPlace(),
            "isLatLongAvailable" to event.getIsLatLongAvailable()
        )
    }

    fun isAlreadyFetched() : Boolean =
        isAlreadyFetched
}