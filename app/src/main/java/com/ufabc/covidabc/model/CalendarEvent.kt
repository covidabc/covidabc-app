package com.ufabc.covidabc.model

import java.io.Serializable
import java.util.*

class CalendarEvent : Serializable {
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

    fun getFormatedDate() : String{
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
