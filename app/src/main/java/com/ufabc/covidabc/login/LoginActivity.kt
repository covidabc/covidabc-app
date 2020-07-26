package com.ufabc.covidabc.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.firebase.auth.FirebaseAuth
import com.ufabc.covidabc.R

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
        emailEditText = findViewById(R.id.login_email_edit_text)
        passwordEditText = findViewById(R.id.login_password_edit_text)
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
            emailEditText.setBackgroundResource(R.drawable.edit_text_normal)
        }

        passwordEditText.addTextChangedListener(){
            passwordEditText.setBackgroundResource(R.drawable.edit_text_normal)
        }
    }

    private fun login() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (isEmailValid() && password.isNotEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        goToMyAccount()
                    } else {
                        Toast.makeText(this, R.string.wrong_credentials, Toast.LENGTH_LONG).show()
                    }
                }

        } else {
            setEditTextErrors()
            Toast.makeText(this, R.string.fill_in_fields, Toast.LENGTH_LONG).show()
        }
    }

    private fun goToMyAccount() {
        val currentUser = mAuth.currentUser!!

        if (currentUser.isEmailVerified) {
            Toast.makeText(this, R.string.welcome, Toast.LENGTH_LONG).show()
            startActivity(Intent(this, MyAccountActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, R.string.verify_email, Toast.LENGTH_LONG).show()
        }
    }

    private fun isEmailValid(): Boolean {
        val email = emailEditText.text
        return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun setEditTextErrors() {
        if (emailEditText.text.isEmpty()){
            emailEditText.error = getString(R.string.required)
            emailEditText.setBackgroundResource(R.drawable.edit_text_error)
        }
        else if (!isEmailValid()) {
            emailEditText.error = getString(R.string.invalid_email)
            emailEditText.setBackgroundResource(R.drawable.edit_text_error)
        }

        if (passwordEditText.text.isEmpty()){
            passwordEditText.error = getString(R.string.required)
            passwordEditText.setBackgroundResource(R.drawable.edit_text_error)
        }

    }
}