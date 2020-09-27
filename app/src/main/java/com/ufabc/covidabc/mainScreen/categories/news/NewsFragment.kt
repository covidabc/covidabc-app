package com.ufabc.covidabc.mainScreen.categories.news

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.solver.widgets.Snapshot
import androidx.fragment.app.Fragment
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.firebase.ui.firestore.SnapshotParser
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.FirestoreDatabaseOperationListener
import com.ufabc.covidabc.model.faq.FAQ
import com.ufabc.covidabc.model.news.News
import com.ufabc.covidabc.model.news.NewsDAO

class NewsFragment : Fragment() {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewsAndListeners()
        setNews()
    }

    private fun setViewsAndListeners() {
        swipeRefreshLayout = requireView().findViewById(R.id.swipe_refresh_layout_news)

        swipeRefreshLayout.setOnRefreshListener {
            NewsDAO.refreshNews(object : FirestoreDatabaseOperationListener<Boolean> {
                override fun onCompleted(sucess: Boolean) {
                    if (sucess) {
                        configureAdapter(NewsDAO.getNewsQuery())
                    }

                    swipeRefreshLayout.isRefreshing = false
                }
            })
        }
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
            .setQuery(query, config) { docSnapshot ->
                docSnapshot.toObject(News::class.java)!!.apply {
                    setRefPath(docSnapshot.reference.path)
                }
            }
            .build()


        view?.findViewById<RecyclerView>(R.id.recycler_view_news).apply {
            val recyclerView = this
            recyclerView!!.layoutManager = LinearLayoutManager(App.appContext)
            recyclerView.adapter = NewsAdapter(options)

        }
    }
}