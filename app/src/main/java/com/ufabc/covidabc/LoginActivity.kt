package com.ufabc.covidabc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.ufabc.covidabc.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var loginButon: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setViews()
        setListeners()
    }

    private fun setViews() {
        loginButon = findViewById<Button>(R.id.login_button)
    }

    private fun setListeners() {
        loginButon.setOnClickListener() {
            goToFeed()
        }
    }

    private fun goToFeed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}