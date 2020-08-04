package com.ufabc.covidabc.mainScreen.categories.event

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.FirestoreDatabaseOperationListener
import com.ufabc.covidabc.model.event.CalendarEvent
import com.ufabc.covidabc.model.event.CalendarEventDAO
import kotlinx.android.synthetic.main.activity_create_event.*
import java.util.*


class CreateEditEventActivity : AppCompatActivity() {
    private lateinit var eventTitleEditText : EditText
    private lateinit var eventPlaceBtn : Button
    private lateinit var eventDescriptionEditText : EditText
    private lateinit var eventTypeSpinner : Spinner
    private lateinit var createEventButton : Button
    private lateinit var pickDateButton : Button
    private lateinit var eventDate : Date
    private lateinit var placeTextHolder : EditText

    private var event = CalendarEvent()

    private var createMode = true

    private val SECOND_ACTIVITY_REQUEST_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)

        setViews()
        setListeners()

        intent?.getSerializableExtra(App.EVENT_EXTRA)?.apply {
            val oldEvent = this as CalendarEvent
            event.setRefPath(oldEvent.getRefPath())
            setEditEvent(oldEvent)
        }
    }

    private fun setEditEvent(oldEvent: CalendarEvent) {
        eventTitleEditText.setText(oldEvent.getTitle())
        eventDescriptionEditText.setText(oldEvent.getDescription())
        placeTextHolder.visibility = View.VISIBLE
        placeTextHolder.setText(oldEvent.getPlace())

        eventDate = oldEvent.getDate()

        event.setDate(eventDate)
        pickDateButton.text = event.getFormatedDate()

        createEventButton.setText(getString(R.string.text_edit))
        createMode = false
    }

    private fun setViews() {
        eventTitleEditText = findViewById(R.id.event_title_edit_text)
        eventPlaceBtn = findViewById(R.id.event_location_map)
        eventDescriptionEditText = findViewById(R.id.event_description_edit_text)
        createEventButton = findViewById(R.id.create_event_button)
        pickDateButton = findViewById(R.id.pick_date_button)
        eventTypeSpinner = findViewById(R.id.event_type_spinner)
        placeTextHolder = findViewById(R.id.event_location_addr)

        eventDescriptionEditText.isFocusableInTouchMode = true

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
                || placeTextHolder.text.isEmpty() || !(this::eventDate.isInitialized)
                || placeTextHolder.isGone

    private fun setListeners() {
        createEventButton.setOnClickListener {
            if(isAnyEditTextEmpty()){
                setEditTextErrors()
                Toast.makeText(applicationContext, R.string.fill_in_fields, Toast.LENGTH_SHORT).show()
            }
            else {
                this.event.apply {
                    setTitle(eventTitleEditText.text.toString())
                    setEventType(eventTypeSpinner.selectedItem as CalendarEvent.EventType)
                    setDescription(eventDescriptionEditText.text.toString())
                    setPlace(placeTextHolder.text.toString())
                    setDate(eventDate)
                }

                when (createMode) {
                    true -> createEvent()
                    false -> editEvent()
                }
            }
        }

        eventTitleEditText.addTextChangedListener {
            eventTitleEditText.setBackgroundResource(R.drawable.edit_text_normal);
        }

        eventPlaceBtn.setOnClickListener { it  ->
            callActivity(findViewById<View>(android.R.id.content).getRootView())
            eventPlaceBtn.setBackgroundResource(R.drawable.button_shape)
            placeTextHolder.setBackgroundResource(R.drawable.edit_text_normal)

        }

        eventDescriptionEditText.addTextChangedListener {
            eventDescriptionEditText.setBackgroundResource(R.drawable.edit_text_normal);
        }

        pickDateButton.setOnClickListener{
            val cal = Calendar.getInstance()

            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
                eventDate = GregorianCalendar(year, month, day, 0, 0).time
                event.setDate(eventDate)
                pickDateButton.text = event.getFormatedDateYr()
            }

            DatePickerDialog(
                this,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()

            pickDateButton.setBackgroundResource(R.drawable.button_shape)
        }
    }

    private fun setEditTextErrors() {
        if(eventTitleEditText.text.isEmpty()){
            eventTitleEditText.error = getString(R.string.required)
            eventTitleEditText.setBackgroundResource(R.drawable.edit_text_error)
        }

        if(placeTextHolder.text.isEmpty() && !(placeTextHolder.isGone)){
            placeTextHolder.error = getString(R.string.required)
            placeTextHolder.setBackgroundResource(R.drawable.edit_text_error)
        }

        if(eventDescriptionEditText.text.isEmpty()) {
            eventDescriptionEditText.error = getString(R.string.required)
            eventDescriptionEditText.setBackgroundResource(R.drawable.edit_text_error)
        }

        if (!this::eventDate.isInitialized) {
            pickDateButton.setBackgroundResource(R.drawable.error_button_shape)
        }

        if (placeTextHolder.isGone ) {
            eventPlaceBtn.setBackgroundResource(R.drawable.error_button_shape)
        }

    }

    private fun callActivity(view: View) {
        val intent = Intent(this, EventMapsLocationActivity::class.java)
        startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE)

    }

    override fun onActivityResult( requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Get String data from Intent
                event.setLongitude(data?.getStringExtra(App.LONGITUDE_EXTRA)?.toDouble() ?: 0.0)
                event.setLatitude(data?.getStringExtra(App.LATITUDE_EXTRA)?.toDouble() ?: 0.0)
                event.setIsLatLongAvailable(data?.getBooleanExtra(App.IS_LAT_LONG_AVAILABLE_EXTRA, false))
                this.placeTextHolder.setText(data?.getStringExtra(App.ADDRESS_EXTRA).toString())
                this.placeTextHolder.visibility = View.VISIBLE
            }
        }
    }

    private fun createEvent() {
        CalendarEventDAO.addEvent(this.event, object : FirestoreDatabaseOperationListener<Boolean> {
            override fun onCompleted(sucess: Boolean) {
                if (sucess) {
                    Toast.makeText(applicationContext, R.string.create_event_sucess, Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(applicationContext, R.string.create_event_failure, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun editEvent() {
        CalendarEventDAO.editEvent(this.event, object : FirestoreDatabaseOperationListener<Boolean> {
            override fun onCompleted(sucess: Boolean) {
                if (sucess) {
                    Toast.makeText(applicationContext, R.string.edit_event_sucess, Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(applicationContext, R.string.edit_event_failure, Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    // Esta funçao verifica se o foco está fora da região de input de texto. Se tiver, ela remove o teclado
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val v = currentFocus
        if (v != null && (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) &&
            v is EditText &&
            !v.javaClass.name.startsWith("android.webkit.")
        ) {
            val sourceCoordinates = IntArray(2)
            v.getLocationOnScreen(sourceCoordinates)
            val x = ev.rawX + v.getLeft() - sourceCoordinates[0]
            val y = ev.rawY + v.getTop() - sourceCoordinates[1]
            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom()) {
                hideKeyboard(this)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun hideKeyboard(activity: Activity?) {
        if (activity != null && activity.window != null) {
            activity.window.decorView
            (activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)?.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
        }
    }
}

