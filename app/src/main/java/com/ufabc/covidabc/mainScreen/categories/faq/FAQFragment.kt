package com.ufabc.covidabc.mainScreen.categories.faq

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.common.util.concurrent.HandlerExecutor
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.faq.FAQ
import com.ufabc.covidabc.model.faq.FAQDAO
import com.ufabc.covidabc.model.FirestoreDatabaseOperationListener
import java.sql.Time
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet


class FAQFragment : Fragment() {

    private val SPLIT_REGEX: Regex = "[^\\w']+".toRegex()

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var faqRecyclerView: RecyclerView
    private lateinit var searchView : SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_faq, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewsAndListeners()
        setFAQ()
    }

    private fun setViewsAndListeners() {
        swipeRefreshLayout = requireView().findViewById(R.id.swipe_refresh_layout_faq)
        faqRecyclerView = requireView().findViewById(R.id.recycler_view_faq)
        searchView = requireView().findViewById(R.id.search_view_quiz)

        swipeRefreshLayout.setOnRefreshListener {
            FAQDAO.refreshFAQ(object : FirestoreDatabaseOperationListener<Boolean> {
                override fun onCompleted(sucess: Boolean) {
                    if (sucess) {
                        populateFAQ(FAQDAO.getFAQArray())
                    }

                    swipeRefreshLayout.isRefreshing = false
                }
            })
        }


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            private var timer = Timer()

            override fun onQueryTextSubmit(str: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(str: String?): Boolean {
                timer.cancel()
                swipeRefreshLayout.isRefreshing = true

                timer = Timer()
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        ContextCompat.getMainExecutor(App.appContext)
                            .execute {
                                populateFAQ(filterFaq(str))
                            }

                        swipeRefreshLayout.isRefreshing = false
                    }
                }, 600)

                return false
            }
        })
    }

    private fun filterFaq(filter : String?) : ArrayList<FAQ> {
        if (filter.isNullOrEmpty())
            return FAQDAO.getFAQArray()

        val filteredFaq= arrayListOf<FAQ>()

        for (faq in FAQDAO.getFAQArray()) {
            if (searchMatches(faq, filter)) {
                filteredFaq.add(faq)
            }
        }

        return filteredFaq
    }

    private fun searchMatches(faq : FAQ, filter: String) : Boolean {
        val tokens = filter
            .toLowerCase(Locale.getDefault())
            .split(SPLIT_REGEX)
            .toHashSet()

        val initialSize = tokens.size

        val questionWords = faq.getQuestion()
            .toLowerCase(Locale.getDefault())
            .split(SPLIT_REGEX)

        for (word in questionWords) {
            if (tokens.contains(word))
                tokens.remove(word)
        }

        return initialSize != tokens.size && tokens.size < 2
    }

    private fun setFAQ() {
        if (FAQDAO.isAlreadyFetched()) {
            populateFAQ(FAQDAO.getFAQArray())
        }
        else {
            val dialog = ProgressDialog(this.context).apply {
                this.setMessage("Aguarde...")
                this.show()
            }

            FAQDAO.refreshFAQ(object : FirestoreDatabaseOperationListener<Boolean> {
                override fun onCompleted(sucess: Boolean) {
                    when (sucess) {
                        true -> populateFAQ(FAQDAO.getFAQArray())
                        false -> Toast.makeText(App.appContext, R.string.get_faq_failure, Toast.LENGTH_LONG).show()
                    }

                    dialog.cancel()
                }
            })
        }
    }

    private fun populateFAQ(faqArray : ArrayList<FAQ>) {
        faqRecyclerView.apply {
            val recyclerView = this
            recyclerView.layoutManager = LinearLayoutManager(App.appContext)
            recyclerView.adapter = FAQAdapter(faqArray)
        }
    }
}