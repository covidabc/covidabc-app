package com.ufabc.covidabc.mainScreen.categories.event

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.FirestoreDatabaseOperationListener
import com.ufabc.covidabc.model.event.CalendarEvent
import com.ufabc.covidabc.model.event.CalendarEventDAO


class EventDescriptionActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var eventTitleTextView : TextView
    private lateinit var eventPlaceTextView: TextView
    private lateinit var eventDateTextView: TextView
    private lateinit var eventDescriptionTextView: TextView
    private lateinit var event : CalendarEvent
    private lateinit var eventDescriptionMapView : MapView
    private lateinit var eventDescriptionToolbar : Toolbar
    private var latlong : LatLng = LatLng(0.0, 0.0)

    private val mAuth : FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_description)

        setViews()
        event = intent.getSerializableExtra(App.EVENT_EXTRA) as CalendarEvent
        populateFAQ()

        eventDescriptionMapView.onCreate(savedInstanceState)

        if (latlong.latitude == 0.0 && latlong.longitude == 0.0) {
            eventDescriptionMapView.visibility = View.INVISIBLE
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.event_description_map_view) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setViews() {
        eventTitleTextView = findViewById(R.id.event_title_text_view)
        eventPlaceTextView = findViewById(R.id.event_place_text_view)
        eventDateTextView = findViewById(R.id.event_date_text_view)
        eventDescriptionTextView = findViewById(R.id.event_description_text_view)
        eventDescriptionMapView = findViewById(R.id.event_description_map_view)

        eventDescriptionToolbar = findViewById(R.id.event_description_toolbar)

        if (isUserLoggedIn()) {
            eventDescriptionToolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_delete_event -> confirmDeletEventDialog()
                    R.id.action_edit_event -> editEvent()
                    else -> super.onOptionsItemSelected(it)
                }

                true
            }
        }
        else {
            eventDescriptionToolbar.visibility = View.GONE
        }
    }

    private fun isUserLoggedIn() : Boolean = mAuth.currentUser != null


    private fun confirmDeletEventDialog() {
        val builder = AlertDialog.Builder(this@EventDescriptionActivity).apply {
            setTitle(getString(R.string.text_aviso))
            setMessage(getString(R.string.confirm_event_delete))
            setPositiveButton(getString(R.string.confirm_sim) ){ dialog, which ->
                deleteEvent()
                dialog.dismiss()
            }
            setNegativeButton(getString(R.string.confirm_não)){ dialog, wich ->
                dialog.dismiss()
            }
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun deleteEvent() {
        CalendarEventDAO.deleteEvent(this.event, object: FirestoreDatabaseOperationListener<Boolean> {
            override fun onCompleted(sucess: Boolean) {
                if (sucess) {
                    Toast.makeText(App.appContext, R.string.delete_sucess, Toast.LENGTH_LONG).show()
                    finish()
                }
                else {
                    Toast.makeText(App.appContext, R.string.delete_failure, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun editEvent() {
        Intent(this, CreateEditEventActivity::class.java).apply {
            putExtra(App.EVENT_EXTRA, event)
            startActivity(this)
        }

        finish()
    }

    private fun populateFAQ() {
        eventTitleTextView.text = event.getTitle()
        eventPlaceTextView.text = event.getPlace()

        eventDateTextView.text = event.getFormatedDate()

        eventDescriptionTextView.text = event.getDescription()
        latlong = LatLng(event.getLatitude(), event.getLongitude())
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
                clear()
                addMarker(
                    MarkerOptions()
                        .position(latlong)
                        .title("Local")
                        .visible(true)
                )
                animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            latlong.latitude,
                            latlong.longitude
                        ), 15f
                    )
                )
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
    }
}


