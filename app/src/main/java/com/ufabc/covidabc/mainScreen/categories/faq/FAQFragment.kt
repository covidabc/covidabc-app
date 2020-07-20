package com.ufabc.covidabc.mainScreen.categories.faq

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.faq.FAQ
import com.ufabc.covidabc.model.faq.FAQDAO
import com.ufabc.covidabc.model.FirestoreDatabaseOperationListener


class FAQFragment : Fragment() {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var faqRecyclerView: RecyclerView

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
        faqRecyclerView = requireView().findViewById<RecyclerView>(R.id.recycler_view_faq)

        swipeRefreshLayout.setOnRefreshListener {
            FAQDAO.refreshFAQ(object : FirestoreDatabaseOperationListener<Boolean> {
                override fun onCompleted(sucess: Boolean) {
                    if (sucess) {
                        faqRecyclerView.adapter?.notifyDataSetChanged()
                    }

                    swipeRefreshLayout.isRefreshing = false
                }
            })
        }
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
            recyclerView!!.layoutManager = LinearLayoutManager(App.appContext)
            recyclerView.adapter = FAQAdapter(faqArray)
        }
    }
}