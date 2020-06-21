package com.ufabc.covidabc.ui.main.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.CalendarDate
import com.ufabc.covidabc.model.CalendarEvent
import java.util.*
import kotlin.collections.ArrayList

class EventFragment : Fragment() {

    lateinit var dates : ArrayList<CalendarDate>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setEvents()
        populateCalendar()
    }

    fun setEvents() {
        dates = arrayListOf()
        dates.add(CalendarDate("Junho"))
        dates.add(CalendarDate("Agosto"))

        for (i in 1..5) {
            dates[0].addEvent(CalendarEvent("Doação de comida $i"))
            dates[1].addEvent(CalendarEvent("Doação de comida $i"))
        }
    }

    private fun populateCalendar() {
        view?.findViewById<RecyclerView>(R.id.recycler_view_calendar).apply {
            val recyclerView = this
            recyclerView!!.layoutManager = LinearLayoutManager(App.appContext)
            recyclerView.adapter = CalendarAdapter(dates)
        }
    }
}