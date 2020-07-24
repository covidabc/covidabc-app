package com.ufabc.covidabc.mainScreen.categories.news

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.SnapshotParser
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.FirestoreDatabaseOperationListener
import com.ufabc.covidabc.model.faq.FAQDAO
import com.ufabc.covidabc.model.news.News
import com.ufabc.covidabc.model.news.NewsDAO

class NewsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNews()
    }

    private fun setNews() {
        if (NewsDAO.isAlreadyFetched()) {
            configureAdapter(NewsDAO.getNewsQuery())
        }
        else {
            val dialog = ProgressDialog(this.context).apply {
                this.setMessage("Aguarde...")
                this.show()
            }

            NewsDAO.refreshNews(object : FirestoreDatabaseOperationListener<Boolean> {
                override fun onCompleted(sucess: Boolean) {
                    when (sucess) {
                        true -> configureAdapter(NewsDAO.getNewsQuery())
                        false -> Toast.makeText(App.appContext, R.string.get_faq_failure, Toast.LENGTH_LONG).show()
                    }

                    dialog.cancel()
                }
            })
        }

    }

    private fun configureAdapter(query : Query) {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPrefetchDistance(10)
            .setPageSize(20)
            .build()

        val options : FirestorePagingOptions<News> = FirestorePagingOptions.Builder<News>()
            .setLifecycleOwner(this)
            .setQuery(query, config, News::class.java)
            .build()

        view?.findViewById<RecyclerView>(R.id.recycler_view_news).apply {
            val recyclerView = this
            recyclerView!!.layoutManager = LinearLayoutManager(App.appContext)
            recyclerView.adapter = NewsAdapter(options)
        }
    }
}