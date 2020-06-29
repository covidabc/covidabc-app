package com.ufabc.covidabc.model

import java.util.*

class CalendarEvent {
    private lateinit var eventName: String
    private lateinit var description : String
    private lateinit var place: String
    private lateinit var eventDate: Calendar

    constructor() {
        val eventDate = Calendar.getInstance().apply {
            this.set(Calendar.YEAR, 1999);
            this.set(Calendar.MONTH, 7);
            this.set(Calendar.DAY_OF_MONTH, 26);
        }

        this.eventDate = eventDate
    }

    constructor(eventName: String, description: String, eventDate: Calendar, place: String) {
        this.eventName = eventName
        this.description = description
        this.eventDate = eventDate
        this.place = place
    }

    fun getEventName() = this.eventName
    fun getDescription() = this.description
    fun getPlace() = this.place
    fun getDate() = this.eventDate
    
}
