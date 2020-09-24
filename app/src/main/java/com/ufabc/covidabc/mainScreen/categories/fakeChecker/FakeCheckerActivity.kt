package com.ufabc.covidabc.mainScreen.categories.fakeChecker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.json.responseJson
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R

class FakeCheckerActivity : AppCompatActivity() {

    private val API_LINK : String = "https://nilc-fakenews.herokuapp.com/ajax/check/"
    private val API_MODEL: String = "unigramas"
    private val SOURCE_LINK: String = "https://nilc-fakenews.herokuapp.com/about"

    private lateinit var sendButton : Button
    private lateinit var newsTextView: EditText
    private lateinit var sourceLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fake_checker)

        setViews()
    }

    private fun setViews() {
        sendButton = findViewById(R.id.fake_check_button)
        newsTextView = findViewById(R.id.fake_checker_edit_text)
        sourceLink = findViewById(R.id.fake_checker_source_link)

        sendButton.setOnClickListener {
            val text = newsTextView.text.toString()

            if (text.length < 100) {
                Toast.makeText(this, R.string.small_text_error, Toast.LENGTH_LONG)
                    .show()
            } else {
                checkFakeNews(text)
            }
        }

        sourceLink.setOnClickListener {
            openNewTabWindow(SOURCE_LINK)
        }
    }

    private fun openNewTabWindow(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        App.appContext.startActivity(browserIntent)
    }

    private fun showResultDialog(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("Continuar") {dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun checkFakeNews(text: String) {
        val formData = listOf(
            "text" to text,
            "model" to API_MODEL
        )

        Fuel.upload(API_LINK, Method.POST, formData)
            .response { _, response, _ ->

                when (response.responseMessage) {
                    "OK" -> {
                        val body = response.body().asString("application/json")
                        val jsonBody = ObjectMapper().readValue<MutableMap<String, String>>(body)
                        val result: String? = jsonBody["result"]

                        val message = when (result!!) {
                            "REAL" -> getString(R.string.text_true_news)
                            else -> getString(R.string.text_fake_news)
                        }
                        showResultDialog(message)
                    }

                    else ->  Toast.makeText(this, R.string.an_error_occured, Toast.LENGTH_LONG).show()
                }
            }
    }

}