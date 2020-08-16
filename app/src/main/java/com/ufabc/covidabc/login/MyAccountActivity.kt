package com.ufabc.covidabc.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.ufabc.covidabc.R
import com.ufabc.covidabc.features.InventoryManagementActivity
import com.ufabc.covidabc.mainScreen.categories.event.CreateEditEventActivity

class MyAccountActivity : AppCompatActivity() {

    private lateinit var logoutButton : Button
    private lateinit var inventoryManagementButton : Button
    private lateinit var createEventButton : Button

    private val mAuth =  FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_account)

        setViews()
        setListeners()
    }

    private fun setViews() {
        inventoryManagementButton = findViewById(R.id.inventory_management_button)
        logoutButton = findViewById(R.id.logout_button)
        createEventButton = findViewById(R.id.create_event_button)
    }

    private fun setListeners() {
        inventoryManagementButton.setOnClickListener {
            startActivity(Intent(this, InventoryManagementActivity::class.java))
        }

        createEventButton.setOnClickListener {
            startActivity(Intent(this, CreateEditEventActivity::class.java))
        }

        logoutButton.setOnClickListener {
            mAuth.signOut()
            Toast.makeText(this, R.string.sign_out, Toast.LENGTH_LONG).show()
            finish()
        }
    }
}