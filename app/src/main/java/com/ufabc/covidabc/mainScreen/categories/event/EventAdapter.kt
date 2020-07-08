package com.ufabc.covidabc.mainScreen.categories.event

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.CalendarEvent
import kotlin.collections.ArrayList

class EventAdapter(private val events: ArrayList<CalendarEvent>): RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var eventHeader : LinearLayout = itemView.findViewById(R.id.event_header)
        var eventBody : RelativeLayout = itemView.findViewById(R.id.event_body)
        var eventTitle : TextView = itemView.findViewById(R.id.event_title)
        var eventDescription: TextView = itemView.findViewById(R.id.event_description)
        var eventDate: TextView = itemView.findViewById(R.id.event_date)
        var eventPlace: TextView = itemView.findViewById(R.id.event_place)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_event, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        events[position].apply {
            holder.eventHeader.setBackgroundColor(getHeaderColor(this.getEventType()))
            holder.eventTitle.text = this.getTitle()
            holder.eventDescription.text = this.getDescription()
            holder.eventDate.text = this.getFormatedDate()
            holder.eventPlace.text = this.getPlace()

            holder.eventBody.setOnClickListener {
                val intent = Intent(it.context, EventDescriptionActivity::class.java)
                intent.putExtra(App.EVENT_EXTRA, this)
                ContextCompat.startActivity(it.context, intent, null)
            }
        }
    }

    private fun getHeaderColor(eventType : CalendarEvent.EventType): Int {
        return when (eventType) {
            CalendarEvent.EventType.DONATION -> ContextCompat.getColor(App.appContext, android.R.color.holo_red_light)
            CalendarEvent.EventType.COLLECTION ->  ContextCompat.getColor(App.appContext, android.R.color.holo_green_light)
            CalendarEvent.EventType.DEMO ->  ContextCompat.getColor(App.appContext, android.R.color.holo_blue_light)
        }
    }

}