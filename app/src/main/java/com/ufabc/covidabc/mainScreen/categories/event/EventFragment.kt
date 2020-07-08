package com.ufabc.covidabc.mainScreen.categories.event

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.CalendarEvent
import com.ufabc.covidabc.model.CalendarEventDAO
import com.ufabc.covidabc.model.FirestoreDatabaseOperationListener
import kotlin.collections.ArrayList

class EventFragment : Fragment() {

    private lateinit var events : ArrayList<CalendarEvent>
    private lateinit var createEventFAB : FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViews(view)
        setListeners()

        setEvents()
        //populateEvents()
    }

    private fun setViews(view: View) {
        createEventFAB = view.findViewById(R.id.fab_event_post)
    }

    private fun setListeners() {
        createEventFAB.setOnClickListener{
            startActivity(Intent(App.appContext, CreateEventActivity::class.java))
        }
    }

    private fun setEvents() {
        CalendarEventDAO.getAllEvents(object: FirestoreDatabaseOperationListener<ArrayList<CalendarEvent>> {
            override fun onSuccess(result: ArrayList<CalendarEvent>) {
                events = result
                populateEvents()
            }

            override fun onFailure() {
                Toast.makeText(App.appContext, R.string.get_events_failure, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun populateEvents() {
        view?.findViewById<RecyclerView>(R.id.recycler_view_calendar).apply {
            val recyclerView = this
            recyclerView!!.layoutManager = LinearLayoutManager(App.appContext)
            recyclerView.adapter = EventAdapter(events)
        }
    }
}