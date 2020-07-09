package com.ufabc.covidabc.mainScreen.categories.event

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.widget.addTextChangedListener
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.CalendarEvent
import com.ufabc.covidabc.model.CalendarEventDAO
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
            if(eventTitleEditText.text.isEmpty() || eventPlaceEditText.text.isEmpty() || eventDescriptionEditText.text.isEmpty() || eventDateEditText.text.isEmpty()){
                val text = "Preencha os campos pendentes!"
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()

                if(eventTitleEditText.text.isEmpty()){
                    eventTitleEditText.setError("campo pendente");
                    eventTitleEditText.setBackgroundResource(R.drawable.edittext_error);
                }
                if(eventPlaceEditText.text.isEmpty()){
                    eventPlaceEditText.setError("campo pendente");
                    eventPlaceEditText.setBackgroundResource(R.drawable.edittext_error);
                }
                if(eventDescriptionEditText.text.isEmpty()){
                    eventDescriptionEditText.setError("campo pendente");
                    eventDescriptionEditText.setBackgroundResource(R.drawable.edittext_error);
                }
                if(eventDateEditText.text.isEmpty()){
                    eventDateEditText.setError("campo pendente");
                    eventDateEditText.setBackgroundResource(R.drawable.edittext_error);
                }
            }

            else {
                createEvent()
            }

            eventTitleEditText.addTextChangedListener(){
                eventTitleEditText.setBackgroundResource(R.drawable.edittext_normal);
            }
            eventPlaceEditText.addTextChangedListener(){
                eventPlaceEditText.setBackgroundResource(R.drawable.edittext_normal);
            }
            eventDescriptionEditText.addTextChangedListener(){
                eventDescriptionEditText.setBackgroundResource(R.drawable.edittext_normal);
            }
            eventDateEditText.addTextChangedListener(){
                eventDateEditText.setBackgroundResource(R.drawable.edittext_normal);
            }

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

            eventDateEditText.addTextChangedListener()

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
