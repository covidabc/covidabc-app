package com.ufabc.covidabc.mainScreen.categories.event

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.FirestoreDatabaseOperationListener
import com.ufabc.covidabc.model.event.CalendarEvent
import com.ufabc.covidabc.model.event.CalendarEventDAO
import java.util.*


class CreateEventActivity : AppCompatActivity() {
    private lateinit var eventTitleEditText : EditText
    private lateinit var eventPlaceBtn : Button
    private lateinit var eventDescriptionEditText : EditText
    private lateinit var eventTypeSpinner : Spinner
    private lateinit var createEventButton : Button
    private lateinit var pickDateButton : Button
    private lateinit var eventDate : Date
    private lateinit var placeTextHolder : EditText

    private var latitude  : Double = 0.0
    private var longitude : Double = 0.0
    private lateinit var addr_string : String

    private val SECOND_ACTIVITY_REQUEST_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)

        setViews()
        setListeners()
    }



    private fun setViews() {
        eventTitleEditText = findViewById(R.id.event_title_edit_text)
        eventPlaceBtn = findViewById(R.id.event_location_map)
        eventDescriptionEditText = findViewById(R.id.event_description_edit_text)
        createEventButton = findViewById(R.id.create_event_button)
        pickDateButton = findViewById(R.id.pick_date_button)
        eventTypeSpinner = findViewById(R.id.event_type_spinner)
        placeTextHolder = findViewById(R.id.event_location_addr)

        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(
            this,
            R.layout.custom_spinner_layout, // Custom spinner layout
            CalendarEvent.EventType.values()
        )
        adapter.setDropDownViewResource(R.layout.custom_spinner_item_layout)

        eventTypeSpinner.adapter = adapter
    }

    private fun isAnyEditTextEmpty() : Boolean =
        eventTitleEditText.text.isEmpty() || eventDescriptionEditText.text.isEmpty()

    private fun setListeners() {
        createEventButton.setOnClickListener {
            if(isAnyEditTextEmpty()){
                setEditTextErrors()
                Toast.makeText(applicationContext, R.string.fill_in_fields, Toast.LENGTH_SHORT).show()
            }
            else {
                this.addr_string = placeTextHolder.text.toString()
                createEvent()
            }
        }

        eventTitleEditText.addTextChangedListener {
            eventTitleEditText.setBackgroundResource(R.drawable.edit_text_normal);
        }

        eventPlaceBtn.setOnClickListener { it  ->
            callActivity(findViewById<View>(android.R.id.content).getRootView())
        }

        eventDescriptionEditText.addTextChangedListener {
            eventDescriptionEditText.setBackgroundResource(R.drawable.edit_text_normal);
        }

        pickDateButton.setOnClickListener{
            val cal = Calendar.getInstance()

            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
                eventDate = GregorianCalendar(year, month, day, 0, 0).time
                pickDateButton.text = "$day/$month/$year"
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

//        if(eventPlaceEditText.text.isEmpty()){
//            eventPlaceEditText.error = getString(R.string.required)
//            eventPlaceEditText.setBackgroundResource(R.drawable.edit_text_error)
//        }

        if(eventDescriptionEditText.text.isEmpty()){
            eventDescriptionEditText.error = getString(R.string.required)
            eventDescriptionEditText.setBackgroundResource(R.drawable.edit_text_error)
        }

    }

    /** Called when the user taps the Send button */
    private fun callActivity(view: View) {
        val intent = Intent(this, EventMapsLocationActivity::class.java)
        startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE)

    }

    override fun onActivityResult( requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Get String data from Intent
                this.addr_string = data?.getStringExtra("addr_string").toString()
                this.longitude   = data?.getStringExtra("long_value")?.toDouble() ?: 0.0
                this.latitude    = data?.getStringExtra("lat_value")?.toDouble() ?: 0.0
                this.placeTextHolder.setText(this.addr_string)
                this.placeTextHolder.visibility = View.VISIBLE

            }
        }
    }

    private fun createEvent() {
        val eventType = eventTypeSpinner.selectedItem as CalendarEvent.EventType
        val newEvent = CalendarEvent(
            eventTitleEditText.text.toString(),
            eventType,
            eventDescriptionEditText.text.toString(),
            this.eventDate,
            this.addr_string,
            this.latitude,
            this.longitude
//            eventPlaceEditText.text.toString()
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
