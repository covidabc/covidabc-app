package com.ufabc.covidabc.model.news

import com.google.firebase.firestore.FirebaseFirestore

object NewsDAO {
    private const val NEWS_COLLECTION = "eventos"

    fun addNew(new: News) {
        FirebaseFirestore.getInstance().collection(NEWS_COLLECTION).add(new)
        // TODO: Add onSuccess and onFailure callback
    }
/*
    fun getAllNews(callback: FirestoreDatabaseOperationListener<ArrayList<News>>) {
        FirebaseFirestore.getInstance().collection(NEWS_COLLECTION).get()
            .addOnSuccessListener { result -> callback.onSuccess(documentsToCalendarEvents(result)) }
            .addOnSuccessListener { callback.onFailure() }
    }

    private fun documentsToCalendarEvents(qSnapshot: QuerySnapshot): ArrayList<CalendarEvent> {
        val events = arrayListOf<CalendarEvent>()

        for (document in qSnapshot.documents) {
            document.toObject(CalendarEvent::class.java)?.apply {
                events.add(this)
            }
        }
        return events
    }*/
}