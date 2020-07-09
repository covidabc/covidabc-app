package com.ufabc.covidabc.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.ufabc.covidabc.R
import com.ufabc.covidabc.mainScreen.MainScreenActivity

class LoginActivity : AppCompatActivity() {

    lateinit var loginButon : Button
    lateinit var registerButton : Button
    lateinit var forgotPasswordButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setViews()
        setListeners()
    }


    private fun setViews() {
        loginButon = findViewById(R.id.login_button)
        registerButton = findViewById(R.id.registerButton)
        forgotPasswordButton = findViewById(R.id.forgotPassButton)
    }

    private fun setListeners() {
        loginButon.setOnClickListener() {
            // TODO
        }

        registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        forgotPasswordButton.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }
}