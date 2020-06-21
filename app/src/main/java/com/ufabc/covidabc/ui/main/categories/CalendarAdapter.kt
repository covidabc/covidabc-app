package com.ufabc.covidabc.ui.main.categories

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.CalendarDate
import com.ufabc.covidabc.model.CalendarEvent
import com.ufabc.covidabc.model.FAQ

class CalendarAdapter(private val dates: ArrayList<CalendarDate>): RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dateText: TextView = itemView.findViewById(R.id.date_text)
        var eventRecyclerView: RecyclerView = itemView.findViewById(R.id.recycler_view_event)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_calendar, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dates.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dateText.text = dates[position].date

        holder.eventRecyclerView.layoutManager = LinearLayoutManager(App.appContext)
        holder.eventRecyclerView.adapter = EventAdapter(dates[position].getCalendarEvents())

    }
}