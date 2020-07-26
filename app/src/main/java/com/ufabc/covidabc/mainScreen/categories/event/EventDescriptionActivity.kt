package com.ufabc.covidabc.mainScreen.categories.event

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.event.CalendarEvent


class EventDescriptionActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var eventTitleTextView : TextView
    private lateinit var eventPlaceTextView: TextView
    private lateinit var eventDateTextView: TextView
    private lateinit var eventDescriptionTextView: TextView
    private lateinit var event : CalendarEvent
    private lateinit var eventDescriptionMapView : MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_description)

        setViews()

        eventDescriptionMapView.onCreate(savedInstanceState)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.event_description_map_view) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        event = intent.getSerializableExtra(App.EVENT_EXTRA) as CalendarEvent
        populateFAQ()
    }

    private fun setViews() {
        eventTitleTextView = findViewById(R.id.event_title_text_view)
        eventPlaceTextView = findViewById(R.id.event_place_text_view)
        eventDateTextView = findViewById(R.id.event_date_text_view)
        eventDescriptionTextView = findViewById(R.id.event_description_text_view)
        eventDescriptionMapView = findViewById(R.id.event_description_map_view)
    }

    private fun populateFAQ() {
        eventTitleTextView.text = event.getTitle()
        eventPlaceTextView.text = event.getPlace()
        eventDateTextView.text = event.getFormatedDate()
        eventDescriptionTextView.text = event.getDescription()
    }


    override fun onStart() {
        eventDescriptionMapView.onStart()
        super.onStart()
    }

    override fun onPause() {
        eventDescriptionMapView.onPause()
        super.onPause()
    }

    override fun onResume() {
        eventDescriptionMapView.onResume()

        eventDescriptionMapView.getMapAsync { it ->
            it?.apply {
                val ufabc = LatLng(-23.6444901,-46.530369)
                addMarker(
                    MarkerOptions()
                        .position(ufabc)
                        .title("UFABC")
                        .visible(true)
                )
                moveCamera(CameraUpdateFactory.newLatLng(ufabc))
                setMaxZoomPreference(14.0f)
                setMinZoomPreference(15.0f)

        }
    }
        super.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        eventDescriptionMapView.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onDestroy() {
        eventDescriptionMapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        eventDescriptionMapView.onLowMemory()
        super.onLowMemory()
    }

    override fun onStop() {
        eventDescriptionMapView.onStop()
        super.onStop()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val ufabc = LatLng(37.7750, -122.4183)
        googleMap?.apply {
            addMarker(
                MarkerOptions()
                    .position(ufabc)
                    .title("UFABC")
                    .visible(true)
            )
            moveCamera(CameraUpdateFactory.newLatLng(ufabc))
        }
    }
}


