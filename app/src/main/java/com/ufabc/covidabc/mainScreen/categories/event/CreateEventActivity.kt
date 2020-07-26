package com.ufabc.covidabc.mainScreen.categories.event

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.widget.addTextChangedListener
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.FirestoreDatabaseOperationListener
import com.ufabc.covidabc.model.event.CalendarEvent
import com.ufabc.covidabc.model.event.CalendarEventDAO
import java.util.*

class CreateEventActivity : AppCompatActivity() {
    private lateinit var eventTitleEditText : EditText
    private lateinit var eventPlaceEditText : EditText
    private lateinit var eventDescriptionEditText : EditText
    private lateinit var eventDateEditText : TextView
    private lateinit var eventTypeSpinner : Spinner
    private lateinit var createEventButton : Button
    private lateinit var pickDateButton : Button
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
        pickDateButton = findViewById(R.id.pick_date_button)
        eventTypeSpinner = findViewById(R.id.event_type_spinner)

        // Create an ArrayAdapter using the string array and a default spinner layout
        eventTypeSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            CalendarEvent.EventType.values()
        )
    }

    private fun isAnyEditTextEmpty() : Boolean =
        eventTitleEditText.text.isEmpty() || eventPlaceEditText.text.isEmpty() || eventDescriptionEditText.text.isEmpty() || eventDateEditText.text.isEmpty()

    private fun setListeners() {
        createEventButton.setOnClickListener {
            if(isAnyEditTextEmpty()){
                setEditTextErrors()
                Toast.makeText(applicationContext, R.string.fill_in_fields, Toast.LENGTH_SHORT).show()
            }
            else {
                createEvent()
            }
        }

        eventTitleEditText.addTextChangedListener {
            eventTitleEditText.setBackgroundResource(R.drawable.edit_text_normal);
        }

        eventPlaceEditText.addTextChangedListener {
            eventPlaceEditText.setBackgroundResource(R.drawable.edit_text_normal);
        }

        eventDescriptionEditText.addTextChangedListener {
            eventDescriptionEditText.setBackgroundResource(R.drawable.edit_text_normal);
        }

        pickDateButton.setOnClickListener{
            val cal = Calendar.getInstance()

            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
                eventDate = GregorianCalendar(year, month, day, 0, 0).time
                eventDateEditText.text = "$day/$month/$year"
                eventDateEditText.setBackgroundResource(R.drawable.edit_text_normal);
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

    private fun setEditTextErrors() {
        if(eventTitleEditText.text.isEmpty()){
            eventTitleEditText.error = getString(R.string.required)
            eventTitleEditText.setBackgroundResource(R.drawable.edit_text_error)
        }

        if(eventPlaceEditText.text.isEmpty()){
            eventPlaceEditText.error = getString(R.string.required)
            eventPlaceEditText.setBackgroundResource(R.drawable.edit_text_error)
        }

        if(eventDescriptionEditText.text.isEmpty()){
            eventDescriptionEditText.error = getString(R.string.required)
            eventDescriptionEditText.setBackgroundResource(R.drawable.edit_text_error)
        }

        if(eventDateEditText.text.isEmpty()){
            eventDateEditText.error = getString(R.string.required)
            eventDateEditText.setBackgroundResource(R.drawable.edit_text_error)
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

        CalendarEventDAO.addEvent(newEvent, object : FirestoreDatabaseOperationListener<Boolean> {
            override fun onCompleted(sucess: Boolean) {
                if (sucess) {
                    Toast.makeText(applicationContext, R.string.create_event_sucess, Toast.LENGTH_LONG).show()
                    finish()
                }

                Toast.makeText(applicationContext, R.string.create_event_failure, Toast.LENGTH_LONG).show()
            }
        })
    }
}
