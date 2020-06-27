package com.ufabc.covidabc.mainScreen.categories.Quiz

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ufabc.covidabc.R
import kotlinx.android.synthetic.main.quiz_question.*

class QuizQuestionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_question)

        yesButton.setOnClickListener {
            val intent = Intent(it.context, QuizAnswerActivity::class.java)
            // start your next activity
            startActivity(intent)
        }

        noButton.setOnClickListener {
            val intent = Intent(it.context, QuizAnswerActivity::class.java)
            // start your next activity
            startActivity(intent)
        }
    }




}

