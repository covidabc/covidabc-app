package com.ufabc.covidabc.mainScreen.categories.Event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.CalendarDate
import com.ufabc.covidabc.model.CalendarEvent
import java.util.*
import kotlin.collections.ArrayList

class EventFragment : Fragment() {

    lateinit var events : ArrayList<CalendarEvent>

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
        populateEvents()
    }

    private fun setEvents() {
        events = arrayListOf()

        for (i in 1..5) {
            val eventName = "Doação de comida $i"
            val eventDescription = "Vamos doar comida no Bairo X. Apareça lá"
            val eventDate = Calendar.getInstance().apply {
                this.set(Calendar.YEAR, 1999);
                this.set(Calendar.MONTH, 7);
                this.set(Calendar.DAY_OF_MONTH, 26);
            }
            val eventPlace = "Avenida dos Estados"
            events.add(CalendarEvent(eventName, eventDescription, eventDate, eventPlace))
        }
    }

    private fun populateEvents() {
        view?.findViewById<RecyclerView>(R.id.recycler_view_calendar).apply {
            val recyclerView = this
            recyclerView!!.layoutManager = LinearLayoutManager(App.appContext)
            recyclerView.adapter = EventAdapter(events)
        }
    }
}