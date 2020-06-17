package com.ufabc.covidabc.ui.main.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.FAQ
import kotlinx.android.synthetic.main.fragment_faq.*

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
        populateFAQ()
    }

    private fun setFAQ() {
        faq = arrayListOf()

        for (i in 0..20) {
            faq.add(FAQ("pergunta $i", "resposta $i"))
        }
    }

    private fun populateFAQ() {
        view?.findViewById<RecyclerView>(R.id.recycler_view).apply {
            val recyclerView = this
            recyclerView!!.layoutManager = LinearLayoutManager(App.appContext)
            recyclerView.adapter = FAQAdapter(faq, this@FAQFragment)
            recyclerView.addItemDecoration(DividerItemDecoration(recyclerView?.context, DividerItemDecoration.VERTICAL))
        }
    }
}