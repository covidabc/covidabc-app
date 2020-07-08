package com.ufabc.covidabc.mainScreen.categories.faq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.*

class FAQFragment : Fragment() {

    private lateinit var faq : ArrayList<FAQ>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_faq, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFAQ()
    }

    private fun setFAQ() {
        FAQDAO.getAllEvents(object: FirestoreDatabaseOperationListener<ArrayList<FAQ>> {
            override fun onSuccess(result: ArrayList<FAQ>) {
                faq = result
                populateFAQ()
            }

            override fun onFailure() {
                Toast.makeText(App.appContext, R.string.get_faq_failure, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun populateFAQ() {
        view?.findViewById<RecyclerView>(R.id.recycler_view).apply {
            val recyclerView = this
            recyclerView!!.layoutManager = LinearLayoutManager(App.appContext)
            recyclerView.adapter = FAQAdapter(faq)
            recyclerView.addItemDecoration(DividerItemDecoration(recyclerView?.context, DividerItemDecoration.VERTICAL))
        }
    }
}