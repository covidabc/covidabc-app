package com.ufabc.covidabc.mainScreen.categories.event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ufabc.covidabc.R

class EventMapsLocationActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var gmap : MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_maps_location)

        setViews()
        gmap.onCreate(savedInstanceState)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.event_description_map_view) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

    }

    private fun setViews() {
        this.gmap = findViewById(R.id.set_event_g_map)
    }


    override fun onStart() {
        gmap.onStart()
        super.onStart()
    }

    override fun onPause() {
        gmap.onPause()
        super.onPause()
    }

    override fun onResume() {
        gmap.onResume()

        gmap.getMapAsync { it ->
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
        gmap.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onDestroy() {
        gmap.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        gmap.onLowMemory()
        super.onLowMemory()
    }

    override fun onStop() {
        gmap.onStop()
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