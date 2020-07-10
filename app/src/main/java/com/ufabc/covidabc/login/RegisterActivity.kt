package com.ufabc.covidabc.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.ufabc.covidabc.R

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerButton: Button
    private lateinit var emailEditText: EditText
    private lateinit var confirmEmailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setViews()
        setListeners()
    }

    private fun setViews() {
        registerButton = findViewById(R.id.register_button)
        emailEditText = findViewById(R.id.email_edit_text)
        confirmEmailEditText = findViewById(R.id.confirm_email_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text)
    }

    private fun setListeners() {
        registerButton.setOnClickListener {
            register()
        }
    }

    private fun register() {
        if (isAllInfoFilled()) {
            createUser()
        }
    }

    private fun isAllInfoFilled() : Boolean {
        // TODO: Verificar se campos estao preenchidos
        return true
    }

    private fun createUser() {
        val email = emailEditText.text.toString()
        val password = emailEditText.text.toString()

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener { task ->
            if (task.isSuccessful) {
                mAuth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, R.string.thank_you_register, Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            }

            Toast.makeText(this, R.string.an_error_occured, Toast.LENGTH_LONG).show()
        })
    }
}