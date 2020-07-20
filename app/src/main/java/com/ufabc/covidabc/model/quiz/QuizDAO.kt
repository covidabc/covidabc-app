package com.ufabc.covidabc.model.quiz

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.ufabc.covidabc.model.FirestoreDatabaseOperationListener

object QuizDAO {

    private const val QUIZ_COLLECTION = "quiz"
    private var isAlreadyFetched = false
    private var quizArray : ArrayList<Quiz> = arrayListOf()

    fun refreshQuiz(callback: FirestoreDatabaseOperationListener<Boolean>) {
        FirebaseFirestore.getInstance().collection(QUIZ_COLLECTION).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    quizArray =
                        documentsToQuiz(
                            task.result!!
                        )
                }

                isAlreadyFetched = true
                callback.onCompleted(task.isSuccessful)
            }
    }

    fun getQuizArray() : ArrayList<Quiz> =
        quizArray

    private fun documentsToQuiz(qSnapshot: QuerySnapshot): ArrayList<Quiz> {
            val quiz = arrayListOf<Quiz>()

            for (document in qSnapshot.documents) {
                document.toObject(Quiz::class.java)?.apply {this@QuizDAO
                    quiz.add(this)
                }
            }
            return quiz
    }

    fun isAlreadyFetched() : Boolean =
        isAlreadyFetched
}