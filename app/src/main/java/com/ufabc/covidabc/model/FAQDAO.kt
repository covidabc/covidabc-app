package com.ufabc.covidabc.model

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.protobuf.Empty

object FAQDAO {

    private const val FAQ_COLLECTION = "faq"
    private var isAlreadyFetched = false
    private var faqArray : ArrayList<FAQ> = arrayListOf()

    fun refreshFAQ(callback: FirestoreDatabaseOperationListener<Boolean>) {
        FirebaseFirestore.getInstance().collection(this.FAQ_COLLECTION).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    this.faqArray = documentsToFAQ(task.result!!)
                }

                isAlreadyFetched = true
                callback.onCompleted(task.isSuccessful)
            }
    }

    fun getFAQArray() = this.faqArray

    private fun documentsToFAQ(qSnapshot: QuerySnapshot): ArrayList<FAQ> {
        val faqArray = arrayListOf<FAQ>()

        for (document in qSnapshot.documents) {
            document.toObject(FAQ::class.java)?.apply {
                faqArray.add(this)
            }
        }
        return faqArray
    }

    fun isAlreadyFetched() : Boolean = this.isAlreadyFetched
}