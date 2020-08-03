package com.ufabc.covidabc.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.android.gms.common.internal.StringResourceValueReader
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.ufabc.covidabc.R
import io.grpc.internal.SharedResourceHolder

class RegisterActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var pinEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var secretPhraseImageView: ImageView

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setViews()
        setListeners()
    }

    private fun setViews() {
        nameEditText = findViewById(R.id.name_edit_text)
        registerButton = findViewById(R.id.register_button)
        emailEditText = findViewById(R.id.email_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text)
        pinEditText = findViewById(R.id.pin_edit_text)
        secretPhraseImageView = findViewById(R.id.secret_phrase_image_view)
    }

    private fun setListeners() {
        registerButton.setOnClickListener {
            registerUser()
        }

        nameEditText.addTextChangedListener(){
            nameEditText.setBackgroundResource(R.drawable.edit_text_normal)
        }

        emailEditText.addTextChangedListener(){
            emailEditText.setBackgroundResource(R.drawable.edit_text_normal)
        }

        passwordEditText.addTextChangedListener(){
            passwordEditText.setBackgroundResource(R.drawable.edit_text_normal)
        }

        confirmPasswordEditText.addTextChangedListener(){
            confirmPasswordEditText.setBackgroundResource(R.drawable.edit_text_normal)
        }

        pinEditText.addTextChangedListener {
            pinEditText.setBackgroundResource(R.drawable.edit_text_normal)
        }

        secretPhraseImageView.setOnClickListener {
            Toast.makeText(applicationContext, R.string.secret_phrase_explanation, Toast.LENGTH_LONG).show()
        }
    }

    private fun isEmailValid(): Boolean {
        val email = emailEditText.text
        return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isEveryFieldValid() : Boolean =
        nameEditText.text.isNotEmpty() && isEmailValid() && passwordEditText.text.isNotEmpty()
                && confirmPasswordEditText.text.isNotEmpty() && pinEditText.text.isNotEmpty()
                && confirmPasswordEditText.text.toString() == passwordEditText.text.toString()

    private fun getPin() : Task<QuerySnapshot>
            = FirebaseFirestore.getInstance().collection("pin").get()

    private fun registerUser() {

        if (isEveryFieldValid()) {
            getPin()
                .addOnSuccessListener {
                    val serverPin: String = it.map { snapshot ->
                        snapshot["code"].toString()
                    }.first()

                    if (serverPin == pinEditText.text.toString()) {
                        createUser()
                    }
                    else {
                        Toast.makeText(this, R.string.wrong_pin, Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, R.string.an_error_occured, android.widget.Toast.LENGTH_LONG).show()
//                    Toast.makeText(this, "Senha muito curta", android.widget.Toast.LENGTH_LONG).show()
                }
        }
        else {
            setEditTextErrors()
            Toast.makeText(applicationContext, R.string.fill_in_fields, Toast.LENGTH_SHORT).show()
        }
    }

    private fun createUser() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                setUserName()

                mAuth.currentUser?.sendEmailVerification()
                    ?.addOnSuccessListener {
                        Toast.makeText(this, R.string.thank_you_register, Toast.LENGTH_LONG).show()
                        finish()
                    }
                    ?.addOnFailureListener {  sendErrorMessage(R.string.an_error_occured)
                    Log.e("LOGIN", it.toString())}

            }
            .addOnFailureListener {
                when (it) {
                    is FirebaseAuthWeakPasswordException -> sendErrorMessage(R.string.weak_pswd_error)
                    is FirebaseAuthUserCollisionException -> sendErrorMessage(R.string.collision_email_error)
                    else -> sendErrorMessage(R.string.an_error_occured)
                }
                Log.e("LOGIN", it.toString())
            }

    }


    private fun sendErrorMessage(err:Int) {
        Toast.makeText(this, err, Toast.LENGTH_LONG).show()
    }

    private fun setUserName() {
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(nameEditText.text.toString()).build()

        mAuth.currentUser!!.updateProfile(profileUpdates)
    }

    private fun setEditTextErrors() {
        if (nameEditText.text.isEmpty()){
            nameEditText.error = getString(R.string.required)
            nameEditText.setBackgroundResource(R.drawable.edit_text_error)
        }

        if (emailEditText.text.isEmpty()){
            emailEditText.error = getString(R.string.required)
            emailEditText.setBackgroundResource(R.drawable.edit_text_error)
        } else if (!isEmailValid()) {
            emailEditText.error = getString(R.string.invalid_email)
            emailEditText.setBackgroundResource(R.drawable.edit_text_error)
        }

        if (passwordEditText.text.isEmpty()){
            passwordEditText.error = getString(R.string.required)
            passwordEditText.setBackgroundResource(R.drawable.edit_text_error)
        }

        if (confirmPasswordEditText.text.isEmpty()){
            confirmPasswordEditText.error = getString(R.string.required)
            confirmPasswordEditText.setBackgroundResource(R.drawable.edit_text_error)
        }

        if (passwordEditText.text.toString() != confirmPasswordEditText.text.toString()) {
            passwordEditText.error = getString(R.string.different_passwords)
            passwordEditText.setBackgroundResource(R.drawable.edit_text_error)

            confirmPasswordEditText.error = getString(R.string.different_passwords)
            confirmPasswordEditText.setBackgroundResource(R.drawable.edit_text_error)
        }

        if (pinEditText.text.isEmpty()) {
            pinEditText.error = getString(R.string.required)
            pinEditText.setBackgroundResource(R.drawable.edit_text_error)
        }
    }
}