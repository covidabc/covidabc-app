package com.ufabc.covidabc.mainScreen.categories.Event

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.CalendarDate

class CalendarAdapter(private val dates: ArrayList<CalendarDate>): RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dateText: TextView = itemView.findViewById(R.id.date_text)
        var eventRecyclerView: RecyclerView = itemView.findViewById(R.id.recycler_view_event)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_calendar, parent, false)

        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = dates.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dateText.text = dates[position].date

        holder.eventRecyclerView.apply {
            this.layoutManager = LinearLayoutManager(App.appContext)
            this.adapter  =
                EventAdapter(dates[position].getCalendarEvents())
            this.addItemDecoration(DividerItemDecoration(App.appContext, DividerItemDecoration.VERTICAL))
        }
    }
}