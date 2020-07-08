package com.ufabc.covidabc.mainScreen.categories.quiz

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ufabc.covidabc.R
import kotlinx.android.synthetic.main.quiz_answer.*

class QuizAnswerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_answer)

        val escolha = intent.getIntExtra("escolha", 0)
        val respostaCorreta = intent.getIntExtra("respostaCorreta", 0)
        val explicacao = intent.getStringExtra("explicacao")

        if (escolha == respostaCorreta) {
            imageAnswer.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext, // Context
                    R.drawable.like // Drawable
                )
            )
            textAnswer.setText("Parabens!!")
            explanation.setText(explicacao)
        } else {
            imageAnswer.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext, // Context
                    R.drawable.dislike // Drawable
                )
            )
            textAnswer.setText("Que pena, está errado!!")
            explanation.setText(explicacao)
        }

        nextQuestion_btn.setOnClickListener {
            val intent = Intent(it.context, QuizQuestionActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
            // start your next activity
            startActivity(intent)
            this.finish()
        }

        Exit_btn.setOnClickListener {
            this.finish()
        }

    }




}

