package com.ufabc.covidabc.mainScreen.categories.quiz

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.FirestoreDatabaseOperationListener
import com.ufabc.covidabc.model.quiz.QuizDAO

class QuizFragment : Fragment() {

    private lateinit var linearLayout: LinearLayout
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

        linearLayout = view.findViewById(R.id.linear_layout_quiz)
        quizButton = view.findViewById(R.id.quiz_enter_button)

        quizButton.setOnClickListener {
            setQuizAndStart()
        }
    }

    private fun setQuizAndStart() {
        if (QuizDAO.isAlreadyFetched()) {
            val intent = Intent(App.appContext, QuizQuestionActivity::class.java)
            startActivity(intent)
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
                    val intent = Intent(App.appContext, QuizQuestionActivity::class.java)
                    startActivity(intent)
                }
            })
        }
    }



}