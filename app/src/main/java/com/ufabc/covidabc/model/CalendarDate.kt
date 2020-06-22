package com.ufabc.covidabc.model

import java.util.*
import kotlin.collections.ArrayList

class CalendarDate {
    var date : String
    var events: ArrayList<CalendarEvent>

    constructor(date: String) {
        this.date = date
        events = arrayListOf()
    }

    fun addEvent(event: CalendarEvent) {
        events.add(event)
    }

    fun getCalendarEvents(): ArrayList<CalendarEvent> = events
}