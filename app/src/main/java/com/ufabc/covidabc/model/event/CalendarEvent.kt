package com.ufabc.covidabc.model.event

import com.google.firebase.firestore.Exclude
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
    private lateinit var refPath: String
    private var latitude : Double = 0.0
    private var longitude : Double = 0.0

    constructor()

    constructor(title: String, eventType: EventType, description: String, date: Date, place: String, lat : Double, long : Double) {
        this.title = title
        this.eventType = eventType
        this.description = description
        this.date = date
        this.place = place
        this.latitude = lat
        this.longitude = long
    }

    fun getTitle() = this.title

    fun setTitle(title: String) {
        this.title = title
    }

    fun getEventType() = this.eventType

    fun setEventType(eventType : EventType) {
        this.eventType = eventType
    }

    fun getDescription() = this.description

    fun setDescription(description: String) {
        this.description = description
    }

    fun getPlace() = this.place

    fun setPlace(place : String) {
        this.place = place
    }

    fun getDate() = this.date

    fun setDate(date : Date) {
        this.date = date
    }

    fun getLongitude() : Double = this.longitude

    fun setLongitude(longitude : Double) {
        this.longitude = longitude
    }

    fun getLatitude() : Double = this.latitude

    fun setLatitude(latitude : Double) {
        this.latitude = latitude
    }

    @Exclude
    fun getRefPath() : String = this.refPath

    fun setRefPath(refPath: String)  {
        this.refPath = refPath
    }

    @Exclude
    fun getFormatedDate() : String {
        val calendar = Calendar.getInstance()
        calendar.time = date

        return "${getParsedDayOfTheWeek(calendar)}, ${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH)+1}"
    }

    @Exclude
    fun getFormatedDateYr() : String {
        val calendar = Calendar.getInstance()
        calendar.time = date

        return "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH)+1}/${calendar.get(Calendar.YEAR)}"
    }

    @Exclude
    fun getDay() : String {
        val calendar = Calendar.getInstance()
        calendar.time = date

        return calendar.get(Calendar.DAY_OF_MONTH).toString()
    }

    @Exclude
    fun getMonth() : String {
        val calendar = Calendar.getInstance()
        calendar.time = date

        return getParsedMonth(calendar)
    }

    private fun getParsedMonth(date: Calendar) : String {
        return when (date.get(Calendar.MONTH)) {
            Calendar.JANUARY -> "JAN"
            Calendar.FEBRUARY -> "FEV"
            Calendar.MARCH -> "MAR"
            Calendar.APRIL -> "ABR"
            Calendar.MAY -> "MAI"
            Calendar.JUNE -> "JUN"
            Calendar.JULY -> "JUL"
            Calendar.AUGUST -> "AGO"
            Calendar.SEPTEMBER -> "SET"
            Calendar.OCTOBER -> "OUT"
            Calendar.NOVEMBER -> "NOV"
            else -> "DEZ"
        }
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
