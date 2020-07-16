package com.ufabc.covidabc.mainScreen.categories.quiz

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.Quiz
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

        setQuiz()
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

        val alertDialogBuilder = AlertDialog.Builder(this)

        if (userAnswer == currQuiz.getIsRight()) {
            alertDialogBuilder.setTitle("Você acertou!")
        }
        else {
            alertDialogBuilder.setTitle("Você errou!")
        }

        alertDialogBuilder.setMessage(currQuiz.getExplanation())
        alertDialogBuilder.setNeutralButton("Continuar",
            DialogInterface.OnClickListener { dialog, id ->
                // FIRE ZE MISSILES!
            })

        alertDialogBuilder.show()
    }

    private fun setQuiz() {
        quizzes = arrayListOf()

        for (i in 0..10) {
            quizzes.add(Quiz("pergunta $i", "resposta $i", i % 2 == 0))
        }
    }

    private fun chooseRandomQuiz() {
        val index = Random.nextInt(0, quizzes.size)

        currQuiz = quizzes[index]
        questionTextView.text = currQuiz.getQuestion()
    }
}

