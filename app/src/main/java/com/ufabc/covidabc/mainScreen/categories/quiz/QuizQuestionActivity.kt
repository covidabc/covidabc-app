package com.ufabc.covidabc.mainScreen.categories.quiz

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.mainScreen.categories.faq.FAQAdapter
import com.ufabc.covidabc.model.*
import kotlinx.android.synthetic.main.dialog_quiz.*
import kotlinx.android.synthetic.main.quiz_question.*
import kotlin.random.Random

class QuizQuestionActivity : AppCompatActivity() {

    private lateinit var questionTextView : TextView
    private lateinit var yes_button : Button
    private lateinit var no_button : Button
    private lateinit var next_question_button : Button

    private lateinit var quizzes : ArrayList <Quiz>
    private lateinit var currQuiz : Quiz

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_question)

        quizzes = QuizDAO.getQuizArray()
        setViews()
        setListeners()
        chooseRandomQuiz()
    }



    private fun setViews() {
        questionTextView = findViewById(R.id.quiz_question_text_view)
        yes_button = findViewById(R.id.yes_button)
        no_button = findViewById(R.id.no_button)
        next_question_button = findViewById(R.id.next_question_button)
    }

    private fun setListeners() {
        yes_button.setOnClickListener {
            checkAnswer(true)
        }

        no_button.setOnClickListener {
            checkAnswer(false)
        }

        next_question_button.setOnClickListener {
            chooseRandomQuiz()
        }
    }

    private fun checkAnswer(userAnswer : Boolean) {

        val alertDialog = Dialog(this)

        alertDialog.setContentView(R.layout.dialog_quiz)


        if (userAnswer == currQuiz.getIsRight()) {
            alertDialog.dialog_title.text = "Você acertou!"
        }
        else {
            alertDialog.dialog_title.text = "Você errou!"
        }

        alertDialog.dialog_message.text = currQuiz.getExplanation()


        alertDialog.show()
    }

    private fun chooseRandomQuiz() {
        val index = Random.nextInt(0, quizzes.size)

        currQuiz = quizzes[index]
        questionTextView.text = currQuiz.getQuestion()
    }
}

