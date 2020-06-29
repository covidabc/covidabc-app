package com.ufabc.covidabc.mainScreen.categories.Event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.CalendarEvent
import com.ufabc.covidabc.model.CalendarEventDAO
import java.util.*

class CreateEventActivity : AppCompatActivity() {
    private lateinit var eventTitleEditText : EditText
    private lateinit var eventPlaceEditText : EditText
    private lateinit var eventTypeEditText : EditText
    private lateinit var eventDescriptionEditText : EditText
    private lateinit var createEventButton : Button
    private lateinit var eventTypeSpinner : Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)

        setViews()
        setListeners()
    }

    private fun setViews() {
        eventTitleEditText = findViewById(R.id.event_title_edit_text)
        eventPlaceEditText = findViewById(R.id.event_place_edit_text)
        eventTypeEditText = findViewById(R.id.event_type_edit_text)
        eventDescriptionEditText = findViewById(R.id.event_description_edit_text)
        createEventButton = findViewById(R.id.create_event_button)
        eventTypeSpinner = findViewById(R.id.event_type_spinner)

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.event_type_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            eventTypeSpinner.adapter = adapter
        }
    }

    private fun setListeners() {
        createEventButton.setOnClickListener {
            createEvent()
        }
    }

    private fun createEvent() {
        val eventDate = Date(System.currentTimeMillis())

        val newEvent = CalendarEvent(eventTitleEditText.text.toString(), eventDescriptionEditText.text.toString(), eventDate, eventPlaceEditText.text.toString())
        CalendarEventDAO.addEvent(newEvent)
    }
}