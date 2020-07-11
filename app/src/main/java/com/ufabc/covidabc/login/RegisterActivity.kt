package com.ufabc.covidabc.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.ufabc.covidabc.R

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerButton: Button
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var name_edit_text: EditText

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
        passwordEditText = findViewById(R.id.password_edit_text)
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text)
        name_edit_text = findViewById(R.id.name_edit_text)
    }

    private fun setListeners() {
        registerButton.setOnClickListener {
            register()
        }

        name_edit_text.addTextChangedListener(){
            name_edit_text.setBackgroundResource(R.drawable.edit_text_normal);
        }

        emailEditText.addTextChangedListener(){
            emailEditText.setBackgroundResource(R.drawable.edit_text_normal);
        }

        confirmEmailEditText.addTextChangedListener(){
            confirmEmailEditText.setBackgroundResource(R.drawable.edit_text_normal);
        }

        passwordEditText.addTextChangedListener(){
            passwordEditText.setBackgroundResource(R.drawable.edit_text_normal);
        }

        confirmPasswordEditText.addTextChangedListener(){
            confirmPasswordEditText.setBackgroundResource(R.drawable.edit_text_normal);
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

    private fun isAnyEditTextEmpty() : Boolean =
        name_edit_text.text.isEmpty() || emailEditText.text.isEmpty() || confirmEmailEditText.text.isEmpty() || passwordEditText.text.isEmpty() || confirmPasswordEditText.text.isEmpty()

    private fun createUser() {

        if (isAnyEditTextEmpty()) {
            setEditTextErrors()
            Toast.makeText(applicationContext, R.string.fill_in_fields, Toast.LENGTH_SHORT).show()
        } else {

                val email = emailEditText.text.toString()
                val password = emailEditText.text.toString()

                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, OnCompleteListener { task ->
                        if (task.isSuccessful) {
                            mAuth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
                                if (it.isSuccessful) {
                                    Toast.makeText(this, R.string.thank_you_register, Toast.LENGTH_LONG)
                                        .show()
                                    finish()
                                }
                            }
                        }

                        Toast.makeText(this, R.string.an_error_occured, Toast.LENGTH_LONG).show()
                    })
            }
        }

    private fun setEditTextErrors() {
        if(name_edit_text.text.isEmpty()){
            name_edit_text.error = R.string.required.toString()
            name_edit_text.setBackgroundResource(R.drawable.edit_text_error)
        }

        if(emailEditText.text.isEmpty()){
            emailEditText.error = R.string.required.toString()
            emailEditText.setBackgroundResource(R.drawable.edit_text_error)
        }

        if(confirmEmailEditText.text.isEmpty()){
            confirmEmailEditText.error = R.string.required.toString()
            confirmEmailEditText.setBackgroundResource(R.drawable.edit_text_error)
        }

        if(passwordEditText.text.isEmpty()){
            passwordEditText.error = R.string.required.toString()
            passwordEditText.setBackgroundResource(R.drawable.edit_text_error)
        }

        if(confirmPasswordEditText.text.isEmpty()){
            confirmPasswordEditText.error = R.string.required.toString()
            confirmPasswordEditText.setBackgroundResource(R.drawable.edit_text_error)
        }
    }
}