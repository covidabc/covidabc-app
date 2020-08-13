package com.ufabc.covidabc.model.logger

import java.io.Serializable

class EventLog : Serializable {
    private var name: String
    private var email: String
    private var action: String
    private var date: java.util.Date
    private val eventID: String

    constructor(name: String, email: String, action: String, date: java.util.Date, eventID: String) {
        this.name = name
        this.email = email
        this.action = action
        this.date = date
        this.eventID = eventID
    }

    fun getName() = name
    fun getEmail() = email
    fun getAction() = action
    fun getDate() = date
    fun getEventID() = eventID

}