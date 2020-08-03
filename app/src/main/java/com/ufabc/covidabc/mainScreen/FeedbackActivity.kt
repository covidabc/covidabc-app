package com.ufabc.covidabc.mainScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.event.CalendarEventDAO

class FeedbackActivity : AppCompatActivity() {

    private val FEEDBACK_COLLECTION = "feedback"

    private lateinit var feedbackEditText : EditText
    private lateinit var sendFeedbackButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        setViewsAndListeners()
    }

    private fun setViewsAndListeners() {
        feedbackEditText = findViewById(R.id.feedback_edit_text)
        sendFeedbackButton = findViewById(R.id.send_feedback_button)

        sendFeedbackButton.setOnClickListener {

            if (feedbackEditText.text.isNotBlank())
                sendFeedback()
        }
    }

    private fun sendFeedback() {
        FirebaseFirestore.getInstance().collection(FEEDBACK_COLLECTION)
            .add(mapOf("message" to feedbackEditText.text.toString()))
            .addOnSuccessListener {
                Toast.makeText(this, R.string.send_feedback_sucess, Toast.LENGTH_LONG).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, R.string.send_feedback_failure, Toast.LENGTH_LONG).show()
            }
    }
}