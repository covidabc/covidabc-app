package com.ufabc.covidabc.mainScreen.categories.Event

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.CalendarEvent
import com.ufabc.covidabc.model.CalendarEventDAO
import java.text.SimpleDateFormat
import java.util.*

class CreateEventActivity : AppCompatActivity() {
    private lateinit var eventTitleEditText : EditText
    private lateinit var eventPlaceEditText : EditText
    private lateinit var eventDescriptionEditText : EditText
    private lateinit var eventDateEditText : EditText
    private lateinit var eventTypeSpinner : Spinner
    private lateinit var createEventButton : Button
    private lateinit var eventDate : Date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)

        setViews()
        setListeners()
    }

    private fun setViews() {
        eventTitleEditText = findViewById(R.id.event_title_edit_text)
        eventPlaceEditText = findViewById(R.id.event_place_edit_text)
        eventDescriptionEditText = findViewById(R.id.event_description_edit_text)
        eventDateEditText = findViewById(R.id.event_date_edit_text)
        createEventButton = findViewById(R.id.create_event_button)
        eventTypeSpinner = findViewById(R.id.event_type_spinner)

        // Create an ArrayAdapter using the string array and a default spinner layout
        eventTypeSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            CalendarEvent.EventType.values()
        )
    }

    private fun setListeners() {
        createEventButton.setOnClickListener {
            createEvent()
        }

        eventDateEditText.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener{ _, year, month, day ->
                eventDate = Date(year, month, day)
                eventDateEditText.setText("$day/$month/$year")
            }

            DatePickerDialog(
                this,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun createEvent() {
        val eventType = eventTypeSpinner.selectedItem as CalendarEvent.EventType
        val newEvent = CalendarEvent(
            eventTitleEditText.text.toString(),
            eventType,
            eventDescriptionEditText.text.toString(),
            this.eventDate,
            eventPlaceEditText.text.toString()
        )
        CalendarEventDAO.addEvent(newEvent)
    }
}
