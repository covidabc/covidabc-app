package com.ufabc.covidabc.mainScreen.categories.quiz

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.FirestoreDatabaseOperationListener
import com.ufabc.covidabc.model.quiz.Quiz
import com.ufabc.covidabc.model.quiz.QuizDAO
import com.ufabc.covidabc.model.quiz.QuizGroup

class QuizFragment : Fragment() {

    private val QUIZ_SIZE = 5

    private lateinit var quizButton : Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        quizButton = view.findViewById(R.id.quiz_enter_button)
        quizButton.setOnClickListener {
            initQuiz()
        }
    }

    private fun initQuiz() {
        if (QuizDAO.isAlreadyFetched()) {
            goToQuizActivity()
        }
        else {
            val dialog = ProgressDialog(this.context).apply {
                this.setMessage("Aguarde...")
                this.show()
            }

            QuizDAO.refreshQuiz(object : FirestoreDatabaseOperationListener<Boolean> {
                override fun onCompleted(sucess: Boolean) {
                    if (!sucess) {
                        Toast.makeText(App.appContext, R.string.get_events_failure, Toast.LENGTH_LONG).show()
                    }

                    dialog.cancel()
                    goToQuizActivity()
                }
            })
        }
    }

    private fun goToQuizActivity() {
        val newQuizGroup = getRandomQuestions()

        Intent(App.appContext, QuizQuestionActivity::class.java).apply {
            this.putExtra(App.QUIZ_EXTRA, newQuizGroup)
            startActivity(this)
        }
    }

    private fun getRandomQuestions() : QuizGroup {
        val quizArray = QuizDAO.getQuizArray()
        val selectedQuestions = arrayListOf<Quiz>()
        val selectedIndex = hashSetOf<Int>()

        while (selectedIndex.size < QUIZ_SIZE) {
            val index = (0 until quizArray.size).random()
            selectedIndex.add(index)
            selectedQuestions.add(quizArray[index])
        }

        return QuizGroup(selectedQuestions, QUIZ_SIZE)
    }



}