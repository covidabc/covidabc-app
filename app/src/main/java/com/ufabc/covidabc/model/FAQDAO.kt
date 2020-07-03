package com.ufabc.covidabc.model

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

object FAQDAO {

    private const val FAQ_COLLECTION = "faq"

    fun addFAQ(event: CalendarEvent) {
        FirebaseFirestore.getInstance().collection(FAQDAO.FAQ_COLLECTION).add(event)
        // TODO: Add onSuccess and onFailure callback
    }

    fun getAllEvents(callback: FirestoreDatabaseOperationListener<ArrayList<FAQ>>) {
        FirebaseFirestore.getInstance().collection(FAQDAO.FAQ_COLLECTION).get()
            .addOnSuccessListener { result -> callback.onSuccess(documentsToFAQ(result)) }
            .addOnSuccessListener { callback.onFailure() }
    }

    private fun documentsToFAQ(qSnapshot: QuerySnapshot): ArrayList<FAQ> {
        val faqs = arrayListOf<FAQ>()

        for (document in qSnapshot.documents) {
            document.toObject(FAQ::class.java)?.apply {
                faqs.add(this)
            }
        }
        return faqs
    }
}