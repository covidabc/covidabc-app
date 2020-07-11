package com.ufabc.covidabc.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.ufabc.covidabc.R
import com.ufabc.covidabc.mainScreen.MainScreenActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var loginButon : Button
    private lateinit var registerButton : Button
    private lateinit var forgotPasswordButton : Button
    private lateinit var emailEditText : EditText
    private lateinit var passwordEditText : EditText

    private val mAuth : FirebaseAuth = FirebaseAuth.getInstance()

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
        emailEditText = findViewById(R.id.email_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
    }

    private fun setListeners() {
        loginButon.setOnClickListener() {
            login()
        }

        registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        forgotPasswordButton.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        emailEditText.addTextChangedListener(){
            emailEditText.setBackgroundResource(R.drawable.edit_text_normal);
        }

        passwordEditText.addTextChangedListener(){
            passwordEditText.setBackgroundResource(R.drawable.edit_text_normal);
        }
    }

    private fun login() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password).
            addOnCompleteListener(this, OnCompleteListener { task ->
                if (task.isSuccessful && mAuth.currentUser != null) {
                    goToFeed(mAuth.currentUser)
                } else {
                    Toast.makeText(this, R.string.wrong_credentials, Toast.LENGTH_LONG).show()
                }
            })

        } else {
            setEditTextErrors()
            Toast.makeText(this, R.string.fill_in_fields, Toast.LENGTH_LONG).show()
        }
    }

    private fun goToFeed(currentUser: FirebaseUser?) {
        if (currentUser!!.isEmailVerified) {
            finish()
        } else {

        }
    }

    private fun setEditTextErrors() {

        if(emailEditText.text.isEmpty()){
            emailEditText.error = R.string.required.toString()
            emailEditText.setBackgroundResource(R.drawable.edit_text_error)
        }

        if(passwordEditText.text.isEmpty()){
            passwordEditText.error = R.string.required.toString()
            passwordEditText.setBackgroundResource(R.drawable.edit_text_error)
        }

    }
}