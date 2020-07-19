package com.ufabc.covidabc.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.ufabc.covidabc.R
import androidx.core.widget.addTextChangedListener

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var sendEmailButton: Button

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        setViews()
        setListeners()
    }

    private fun setViews() {
        emailEditText = findViewById(R.id.forgot_email_edit_text)
        sendEmailButton = findViewById(R.id.send_email_button)
    }

    private fun setListeners() {
        sendEmailButton.setOnClickListener {
            forgotPassword()
        }

        emailEditText.addTextChangedListener {
            emailEditText.setBackgroundResource(R.drawable.edit_text_normal)
        }
    }

    private fun forgotPassword() {
        if (isEmailValid()) {
            changePasswordAndReturn()
        }
        else {
            setEditTextErrors()
            Toast.makeText(applicationContext, R.string.fill_in_fields, Toast.LENGTH_SHORT).show()
        }
    }

    private fun isEmailValid(): Boolean {
        val email: String = emailEditText.text.toString()

        return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun changePasswordAndReturn() {
        val email = emailEditText.text.toString()

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, R.string.check_your_email, Toast.LENGTH_LONG).show()
                finish()
            }
            else {
                Toast.makeText(this, R.string.error_send_email, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setEditTextErrors() {
        emailEditText.setBackgroundResource(R.drawable.edit_text_error)

        if (emailEditText.text.isEmpty())
            emailEditText.error = getString(R.string.required)
        else
            emailEditText.error = getString(R.string.invalid_email)
    }

}