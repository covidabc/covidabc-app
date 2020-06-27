package com.ufabc.covidabc.model

import java.util.*

class CalendarEvent {
    private var eventName: String
    private var description : String
    private var place: String
    private var eventDate: Calendar

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
