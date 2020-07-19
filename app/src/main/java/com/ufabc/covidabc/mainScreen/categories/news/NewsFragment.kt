package com.ufabc.covidabc.mainScreen.categories.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.News

class NewsFragment : Fragment() {

    private lateinit var newsArray : ArrayList<News>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNews()
        populateNews()
    }

    private fun setNews() {
        newsArray = arrayListOf<News>()
        for (i in 0..10) {
            newsArray.add(News("titulo $i", "subtitulo $i", "https://pfarma.com.br/images/noticias/coronavirus-covid-19.jpg"))
        }
    }

    private fun populateNews() {
        view?.findViewById<RecyclerView>(R.id.recycler_view_news).apply {
            val recyclerView = this
            recyclerView!!.layoutManager = LinearLayoutManager(App.appContext)
            recyclerView.adapter = NewsAdapter(newsArray)
        }
    }
}