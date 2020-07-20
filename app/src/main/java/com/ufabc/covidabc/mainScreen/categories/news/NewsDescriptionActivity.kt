package com.ufabc.covidabc.mainScreen.categories.news

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.news.News

class NewsDescriptionActivity : AppCompatActivity() {
    private lateinit var newsTitleTextView: TextView
    private lateinit var newsTitleAuxTextView: TextView

    private lateinit var news: News

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_description)

        news = intent.getSerializableExtra(App.NEWS_EXTRA) as News

        setViews()
        populateNEWS()
    }

    private fun setViews() {
        newsTitleTextView = findViewById(R.id.news_title_text_view)
        newsTitleAuxTextView = findViewById(R.id.news_title_aux_text_view)
    }

    private fun populateNEWS() {
        newsTitleTextView.text = news.getTitulo()
        newsTitleAuxTextView.text = news.getTituloAuxiliar()
    }
}