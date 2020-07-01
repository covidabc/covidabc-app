package com.ufabc.covidabc.model

import java.util.*

class CalendarEvent {
    enum class EventType(private val value: String) {
        DONATION("Doação"),
        DEMO("Manifestação"),
        COLLECTION("Arrecadação");

        override fun toString() = value
    }

    private lateinit var eventType: EventType
    private lateinit var title: String
    private lateinit var description : String
    private lateinit var place: String
    private lateinit var date: Date

    constructor()

    constructor(title: String, eventType: EventType, description: String, date: Date, place: String) {
        this.title = title
        this.eventType = eventType
        this.description = description
        this.date = date
        this.place = place
    }

    fun getTitle() = this.title
    fun getEventType() = this.eventType
    fun getDescription() = this.description
    fun getPlace() = this.place
    fun getDate() = this.date
    
}
