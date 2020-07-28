package com.ufabc.covidabc.mainScreen.categories.event

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ufabc.covidabc.R
import java.io.IOException


class EventMapsLocationActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var gmap : MapView
    private lateinit var search_text : EditText
    private var latlong : LatLng = LatLng(0.0, 0.0)
    private lateinit var fabAddAddress : FloatingActionButton
    private var addr_string : String = ""
    private var marker : MarkerOptions = MarkerOptions().position(LatLng(0.0, 0.0))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_maps_location)

        setViews()
        gmap.onCreate(savedInstanceState)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.event_description_map_view) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        setListeners()
    }


    private fun setListeners() {
        fabAddAddress.setOnClickListener { it ->
            if (addr_string.isBlank())
                showAlertDialog("Endereço inválido")
            else
                returnActivity()
        }


        search_text.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE
                || keyEvent.action == KeyEvent.ACTION_DOWN
                || keyEvent.action == KeyEvent.KEYCODE_ENTER) {

                geoLocate()
            }
            false
        }
    }

    private fun returnActivity() {
        val intent = Intent()
        intent.putExtra("addr_string", addr_string)
        intent.putExtra("lat_value", latlong.latitude.toString())
        intent.putExtra("long_value", latlong.longitude.toString())
        setResult(Activity.RESULT_OK, intent)

        finish()
    }

    private fun geoLocate() {
        addr_string = search_text.getText().toString()

        val geocoder = Geocoder(this@EventMapsLocationActivity)
        var list: List<Address> = ArrayList()

        try {
            list = geocoder.getFromLocationName(addr_string, 1)
        } catch (e: IOException) {
            Log.e("Maps", "geoLocate: IOException: " + e.toString())
        }

        if (list.isNotEmpty()) {
            val address = list[0]

            if (address.hasLatitude() && address.hasLongitude()) {
                this.latlong = LatLng(address.latitude, address.longitude)
                setMapFocus(latlong, 15f)
            }

            addr_string = address.getAddressLine(0)
            Toast.makeText(this, addr_string, Toast.LENGTH_SHORT).show();

        } else {
            showAlertDialog("Endereço não encontrado no mapa. Deseja usar assim mesmo?")
        }
    }

    private fun setMapFocus(latlong: LatLng, zoom : Float = 14.0f) {

        this.marker.position(latlong)
        this.marker.title("Local da ação")
        this.marker.visible(true)
        gmap.getMapAsync { it ->
            it?.apply {
                clear()
                addMarker(marker)
                animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            latlong.latitude,
                            latlong.longitude
                        ), zoom
                    )
                )
            }
        }
    }


    private fun setViews() {
        this.gmap = findViewById(R.id.set_event_g_map)
        this.search_text = findViewById(R.id.event_map_loc_search_text)
        this.fabAddAddress = findViewById(R.id.floating_add_address)
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
        setMapFocus(LatLng(-14.235004, -51.92528), 1f)
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

    private fun showAlertDialog(msg : String = "") {
        val builder = AlertDialog.Builder(this@EventMapsLocationActivity)
        builder.setTitle("Aviso")
        builder.setMessage(msg)

        builder.setPositiveButton("Sim"){dialog, which ->
            returnActivity()
            dialog.dismiss()
        }

        builder.setNegativeButton("Não"){dialog, wich ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    override fun onMapReady(googleMap: GoogleMap?) {

    }



}