package com.ufabc.covidabc.mainScreen.categories.faq

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.faq.FAQ

class FAQDescriptionActivity : AppCompatActivity() {

    lateinit var questionText: TextView
    lateinit var answerText: TextView
    lateinit var sourceText: TextView

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
        sourceText = findViewById(R.id.faq_source_text_view)
    }

    private fun populateFAQ() {
        questionText.text = faq.getQuestion()
        answerText.text = faq.getAnswer()
        sourceText.text = "Fonte: " + faq.getSource()
        sourceText.setOnClickListener {
            openNewTabWindow(faq.getSourceURL())
        }
    }
    private fun openNewTabWindow(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        App.appContext.startActivity(browserIntent)
    }

}