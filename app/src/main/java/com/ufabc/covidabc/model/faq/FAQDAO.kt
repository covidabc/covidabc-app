package com.ufabc.covidabc.model.faq

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.ufabc.covidabc.model.FirestoreDatabaseOperationListener

object FAQDAO {

    private const val FAQ_COLLECTION = "faq"
    private var isAlreadyFetched = false
    private var faqArray : ArrayList<FAQ> = arrayListOf()

    fun refreshFAQ(callback: FirestoreDatabaseOperationListener<Boolean>) {
        FirebaseFirestore.getInstance().collection(FAQ_COLLECTION).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    faqArray =
                        documentsToFAQ(task.result!!)
                }

                isAlreadyFetched = true
                callback.onCompleted(task.isSuccessful)
            }
    }

    fun getFAQArray() = faqArray

    private fun documentsToFAQ(qSnapshot: QuerySnapshot): ArrayList<FAQ> {
        val faqArray = arrayListOf<FAQ>()

        for (document in qSnapshot.documents) {
            document.toObject(FAQ::class.java)?.apply {
                faqArray.add(this)
            }
        }
        return faqArray
    }

    fun isAlreadyFetched() : Boolean =
        isAlreadyFetched
}