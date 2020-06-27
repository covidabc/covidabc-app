package com.ufabc.covidabc.mainScreen.categories.Event

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.CalendarEvent

class EventAdapter(private val events: ArrayList<CalendarEvent>): RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var eventDescription: TextView = itemView.findViewById(R.id.event_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_event, parent, false)

        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.eventDescription.text = events[position].getDescription()
    }
}