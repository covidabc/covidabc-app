package com.ufabc.covidabc.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.ufabc.covidabc.R

class MyAccountActivity : AppCompatActivity() {

    private lateinit var logoutButton : Button
    private val mAuth =  FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_account)

        logoutButton = findViewById(R.id.logout_button)
        logoutButton.setOnClickListener {
            mAuth.signOut()
            Toast.makeText(this, R.string.sign_out, Toast.LENGTH_LONG).show()
            finish()
        }
    }
}