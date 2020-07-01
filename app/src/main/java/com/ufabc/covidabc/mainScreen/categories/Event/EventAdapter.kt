package com.ufabc.covidabc.mainScreen.categories.Event

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.CalendarEvent
import java.util.*
import kotlin.collections.ArrayList

class EventAdapter(private val events: ArrayList<CalendarEvent>): RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var eventType : TextView = itemView.findViewById(R.id.event_type)
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
            holder.eventType.text = this.getEventType().toString()
            holder.eventDescription.text = this.getDescription()
            holder.eventDate.text = getParsedDateText(this.getDate())
            holder.eventPlace.text = this.getPlace()
        }
    }

    private fun getParsedDateText(date: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = date

        return "${getParsedDayOfTheWeek(calendar)}, ${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH)}"
    }

    private fun getParsedDayOfTheWeek(date: Calendar) : String {
        return when (date.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> "SEG"
            Calendar.TUESDAY -> "TER"
            Calendar.WEDNESDAY -> "QUA"
            Calendar.THURSDAY -> "QUI"
            Calendar.FRIDAY -> "SEX"
            Calendar.SATURDAY -> "SAB"
            else -> "DOM"
        }
    }
}