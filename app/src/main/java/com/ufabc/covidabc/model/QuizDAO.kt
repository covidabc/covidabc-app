package com.ufabc.covidabc.model

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

object QuizDAO {

        private const val quiz_COLLECTION = "quiz"

        fun addquiz(question: Quiz) {
            FirebaseFirestore.getInstance().collection(QuizDAO.quiz_COLLECTION).add(question)
            // TODO: Add onSuccess and onFailure callback
        }

        fun getAllFAQs(callback: FirestoreDatabaseOperationListener<ArrayList<Quiz>>) {
            FirebaseFirestore.getInstance().collection(QuizDAO.quiz_COLLECTION).get()
                .addOnSuccessListener { result -> callback.onSuccess(
                    com.ufabc.covidabc.model.QuizDAO.documentstoquiz(result)) }
                .addOnFailureListener { callback.onFailure() }
        }

    private fun documentstoquiz(result: QuerySnapshot?): java.util.ArrayList<Quiz> {
        TODO("Not yet implemented")
    }

    private fun documentsToquiz(qSnapshot: QuerySnapshot): ArrayList<Quiz> {
            val quiz = arrayListOf<Quiz>()

            for (document in qSnapshot.documents) {
                document.toObject(Quiz::class.java)?.apply {this@QuizDAO
                    quiz.add(this)
                }
            }
            return quiz
    }

}