package com.ufabc.covidabc.mainScreen.categories.Event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.ufabc.covidabc.R
import kotlinx.android.synthetic.main.activity_create_event.*

class CreateEventActivity : AppCompatActivity() {
    private lateinit var eventNameEditText : EditText
    private lateinit var eventPlaceEditText : EditText
    private lateinit var eventTypeEditText : EditText
    private lateinit var eventDescriptionEditText : EditText
    private lateinit var createEventButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)
    }

    private fun setViews() {
        eventNameEditText = findViewById(R.id.event_name_edit_text)
        eventPlaceEditText = findViewById(R.id.event_place)
        eventTypeEditText = findViewById(R.id.event_type_edit_text)
        eventDescriptionEditText = findViewById(R.id.event_description_edit_text)
        createEventButton = findViewById(R.id.create_event_button)
    }

    private fun setListeners() {

    }
}