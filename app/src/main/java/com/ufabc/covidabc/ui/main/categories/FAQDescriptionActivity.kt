package com.ufabc.covidabc.ui.main.categories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.FAQ

class FAQDescriptionActivity : AppCompatActivity() {

    lateinit var questionText: TextView
    lateinit var answerText: TextView

    lateinit var faq: FAQ

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq_description)

        faq = intent.getSerializableExtra(App.FAQ_EXTRA) as FAQ

        setViews()
        populateFAQ()
    }

    private fun setViews() {
        questionText = findViewById(R.id.question_text)
        answerText = findViewById(R.id.answer_text)
    }

    private fun populateFAQ() {
        questionText.text = faq.question
        answerText.text = faq.answer
    }
}