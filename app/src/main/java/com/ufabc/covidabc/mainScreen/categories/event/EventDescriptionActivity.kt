package com.ufabc.covidabc.mainScreen.categories.event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.event.CalendarEvent

class EventDescriptionActivity : AppCompatActivity() {

    private lateinit var eventTitleTextView : TextView
    private lateinit var eventPlaceTextView: TextView
    private lateinit var eventDateTextView: TextView
    private lateinit var eventDescriptionTextView: TextView

    private lateinit var event : CalendarEvent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_description)

        event = intent.getSerializableExtra(App.EVENT_EXTRA) as CalendarEvent

        setViews()
        populateFAQ()
    }

    private fun setViews() {
        eventTitleTextView = findViewById(R.id.event_title_text_view)
        eventPlaceTextView = findViewById(R.id.event_place_text_view)
        eventDateTextView = findViewById(R.id.event_date_text_view)
        eventDescriptionTextView = findViewById(R.id.event_description_text_view)
    }

    private fun populateFAQ() {
        eventTitleTextView.text = event.getTitle()
        eventPlaceTextView.text = event.getPlace()
        eventDateTextView.text = event.getFormatedDate()
        eventDescriptionTextView.text = event.getDescription()
    }
}