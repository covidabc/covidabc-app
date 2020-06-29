package com.ufabc.covidabc.model

import java.util.*

class CalendarEvent {
    enum class EventType(private val value: String) {
        DONATION("Donation"),
        DEMO("Demo"),
        COLLECTION("Collection");

        override fun toString() = value
    }

    private lateinit var postType: EventType
    private lateinit var title: String
    private lateinit var description : String
    private lateinit var place: String
    private lateinit var date: Date

    constructor()

    constructor(title: String, description: String, date: Date, place: String) {
        this.title = title
        this.description = description
        this.date = date
        this.place = place
    }

    fun getTitle() = this.title
    fun getDescription() = this.description
    fun getPlace() = this.place
    fun getDate() = this.date
    
}
