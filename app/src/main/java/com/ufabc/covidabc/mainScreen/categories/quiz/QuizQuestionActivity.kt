package com.ufabc.covidabc.mainScreen.categories.quiz

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ufabc.covidabc.R
import kotlinx.android.synthetic.main.quiz_question.*
import kotlin.random.Random

class QuizQuestionActivity : AppCompatActivity() {

    val pergunta = arrayOf("Pergunta 1", "Pergunta 2", "Pergunta 3", "Pergunta 4", "Pergunta 5")
    val explicacao = arrayOf("Explicação 1", "Explicação 2", "Explicação 3", "Explicação 4", "Explicação 5")
    val respostaCorreta = arrayOf(1,0,1,1,0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_question)

        val i = Random.nextInt(0,4)
        quiz_questionText.setText(pergunta[i])

        yesButton.setOnClickListener {
            val intent = Intent(it.context, QuizAnswerActivity::class.java)
            intent.putExtra("escolha", 1)
            intent.putExtra("respostaCorreta", respostaCorreta[i])
            intent.putExtra("explicacao", explicacao[i])
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
            // start your next activity
            startActivity(intent)
            this.finish()
        }

        noButton.setOnClickListener {
            val intent = Intent(it.context, QuizAnswerActivity::class.java)
            intent.putExtra("escolha", 0)
            intent.putExtra("respostaCorreta", respostaCorreta[i])
            intent.putExtra("explicacao", explicacao[i])
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
            // start your next activity
            startActivity(intent)
            this.finish()
        }
    }




}

