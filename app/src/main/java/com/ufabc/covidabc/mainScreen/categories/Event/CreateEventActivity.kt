package com.ufabc.covidabc.mainScreen.categories.Event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.CalendarEvent
import com.ufabc.covidabc.model.CalendarEventDAO
import java.util.*

class CreateEventActivity : AppCompatActivity() {
    private lateinit var eventNameEditText : EditText
    private lateinit var eventPlaceEditText : EditText
    private lateinit var eventTypeEditText : EditText
    private lateinit var eventDescriptionEditText : EditText
    private lateinit var createEventButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)

        setViews()
        setListeners()
    }

    private fun setViews() {
        eventNameEditText = findViewById(R.id.event_name_edit_text)
        eventPlaceEditText = findViewById(R.id.event_place_edit_text)
        eventTypeEditText = findViewById(R.id.event_type_edit_text)
        eventDescriptionEditText = findViewById(R.id.event_description_edit_text)
        createEventButton = findViewById(R.id.create_event_button)
    }

    private fun setListeners() {
        createEventButton.setOnClickListener {
            createEvent()
        }
    }

    private fun createEvent() {
        val eventDate = Calendar.getInstance().apply {
            this.set(Calendar.YEAR, 1999);
            this.set(Calendar.MONTH, 7);
            this.set(Calendar.DAY_OF_MONTH, 26);
        }

        val newEvent = CalendarEvent(eventNameEditText.text.toString(), eventDescriptionEditText.text.toString(), eventDate, eventPlaceEditText.text.toString())
        CalendarEventDAO.addEvent(newEvent)
    }
}