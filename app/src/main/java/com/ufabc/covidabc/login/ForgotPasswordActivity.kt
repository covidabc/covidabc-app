package com.ufabc.covidabc.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.ufabc.covidabc.R

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var emailEditText : EditText
    private lateinit var sendEmailButton : Button

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        setViews()
        setListeners()
    }

    private fun setViews() {
        emailEditText = findViewById(R.id.email_edit_text)
        sendEmailButton = findViewById(R.id.send_email_button)
    }

    private fun setListeners() {
        sendEmailButton.setOnClickListener {
            forgotPassword()
        }
    }

    private fun forgotPassword() {
        if (isEmailValid())
            changePasswordAndReturn()
    }

    private fun isEmailValid() : Boolean {
        // TODO : verificar email valido
        return true
    }

    private fun changePasswordAndReturn() {
        val email = emailEditText.text.toString()

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, R.string.check_your_email, Toast.LENGTH_LONG).show()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }
}