package com.ufabc.covidabc.mainScreen.categories.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.quiz.Quiz
import com.ufabc.covidabc.model.quiz.QuizDAO
import com.ufabc.covidabc.model.quiz.QuizGroup

class QuizResultActivity : AppCompatActivity() {

    private lateinit var resultTextView : TextView
    private lateinit var quitQuizButton : Button

    private lateinit var quizGroup: QuizGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)

        this.quizGroup = intent.getSerializableExtra(App.QUIZ_EXTRA) as QuizGroup
        setViews()
        setResult()
    }

    private fun setViews() {
        resultTextView = findViewById(R.id.result_text_view)
        quitQuizButton = findViewById(R.id.quit_quiz_button)

        quitQuizButton.setOnClickListener {
            finish()
        }
    }

    private fun setResult() {
        resultTextView.text = "Você acertou " + quizGroup.getScore() + " de " + quizGroup.getMaxScore() + " questões"
    }
}