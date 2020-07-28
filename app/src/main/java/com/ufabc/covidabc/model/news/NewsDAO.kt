package com.ufabc.covidabc.model.news

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.ufabc.covidabc.model.FirestoreDatabaseOperationListener
import com.ufabc.covidabc.model.faq.FAQDAO

object NewsDAO {
    private const val NEWS_COLLECTION = "news"

    private var isAlreadyFetched = false
    private lateinit var newsQuery: Query

    fun getNewsQuery(): Query = this.newsQuery

    fun isAlreadyFetched() : Boolean = this.isAlreadyFetched

    fun refreshNews(callback: FirestoreDatabaseOperationListener<Boolean>) {
        FirebaseFirestore.getInstance().collection(NEWS_COLLECTION)
            .orderBy("date", Query.Direction.DESCENDING).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    this.newsQuery = task.result!!.query
                }

                isAlreadyFetched = true
                callback.onCompleted(task.isSuccessful)
            }
    }
}