package com.ufabc.covidabc.mainScreen.categories.event

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import kotlinx.android.synthetic.main.dialog_save_map.*
import kotlinx.android.synthetic.main.dialog_unfilled_map.*
import java.io.IOException


class EventMapsLocationActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mapView : MapView
    private lateinit var addressSearchText : EditText
    private var latlong : LatLng = LatLng(0.0, 0.0)
    private lateinit var addAddressFAB : FloatingActionButton
    private var marker : MarkerOptions = MarkerOptions().position(LatLng(0.0, 0.0))

    private var hasPinpoint = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_maps_location)

        setViews()
        mapView.onCreate(savedInstanceState)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.event_description_map_view) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        setListeners()
    }


    private fun setListeners() {
        addAddressFAB.setOnClickListener { it ->
            if (addressSearchText.text.isBlank()) {
                Dialog(this).apply {
                    setCancelable(false)
                    setContentView(R.layout.dialog_unfilled_map)
                    fill_maps_button.setOnClickListener {
                        dismiss()
                    }
                }.show()
            }
            else {
                if (!hasPinpoint) {
                    geoLocate()
                }

                Dialog(this).apply {
                    setCancelable(false)
                    setContentView(R.layout.dialog_save_map)

                    address_dialog_message.text = addressSearchText.text.toString()
                    found_dialog_message.text = when (hasPinpoint) {
                        false -> "Infelizmente não encontramos esse endereço no mapa"
                        true -> "Encontramos seu endereço no mapa"
                    }
                    save_dialog_button.setOnClickListener {
                        dismiss()
                        returnActivity()
                    }
                    edit_dialog_button.setOnClickListener {
                        dismiss()
                    }
                }.show()
            }
        }

        addressSearchText.doOnTextChanged { _, _, _, _ ->
            latlong = LatLng(0.0, 0.0)
            hasPinpoint = false
        }


        addressSearchText.setOnKeyListener { view, i, keyEvent ->
            Log.d("MAPA", "evento")
            true
        }

        addressSearchText.setOnEditorActionListener { _, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE
                || keyEvent.action == KeyEvent.ACTION_DOWN
                || keyEvent.action == KeyEvent.KEYCODE_ENTER) {

                hideKeyboard(this)
                if (!geoLocate()) {
                    Toast.makeText(baseContext, getString(R.string.maps_error), Toast.LENGTH_LONG).show()
                }
            }
            false
        }
    }

    private fun returnActivity() {
        Intent().apply {
            putExtra(App.ADDRESS_EXTRA, addressSearchText.text.toString())
            putExtra(App.LATITUDE_EXTRA, latlong.latitude.toString())
            putExtra(App.LONGITUDE_EXTRA, latlong.longitude.toString())
            setResult(Activity.RESULT_OK, this)
        }

        finish()
    }

    private fun geoLocate(): Boolean {
        val geocoder = Geocoder(this@EventMapsLocationActivity)
        var list: List<Address> = ArrayList()

        try {
            list = geocoder.getFromLocationName(addressSearchText.text.toString(), 1)
        } catch (e: IOException) {
            Log.e("Maps", "geoLocate: IOException: " + e.toString())
        }

        if (list.isNotEmpty()) {
            val address = list[0]

            if (address.hasLatitude() && address.hasLongitude()) {
                this.latlong = LatLng(address.latitude, address.longitude)
                setMapFocus(latlong, 15f)
            }

            addressSearchText.setText(address.getAddressLine(0))
            hasPinpoint = true
            return true
        } else {
            return false
        }
    }

    private fun setMapFocus(latlong: LatLng, zoom : Float = 14.0f) {
        this.marker.position(latlong)
        this.marker.title("Local da ação")
        this.marker.visible(true)
        mapView.getMapAsync { it ->
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
        this.mapView = findViewById(R.id.set_event_g_map)
        this.addressSearchText = findViewById(R.id.event_map_loc_search_text)
        this.addAddressFAB = findViewById(R.id.floating_add_address)
    }


    override fun onStart() {
        mapView.onStart()
        super.onStart()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onResume() {
        mapView.onResume()
        setMapFocus(LatLng(-14.235004, -51.92528), 1f)
        super.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        mapView.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        mapView.onLowMemory()
        super.onLowMemory()
    }

    override fun onStop() {
        mapView.onStop()
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

    private fun hideKeyboard(activity: Activity?) {
        if (activity != null && activity.window != null) {
            activity.window.decorView
            (activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)?.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {

    }
}
