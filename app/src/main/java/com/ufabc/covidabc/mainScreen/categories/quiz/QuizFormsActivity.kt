package com.ufabc.covidabc.mainScreen.categories.quiz

import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.logger.Logger
import com.ufabc.covidabc.model.FirestoreDatabaseOperationListener
import com.ufabc.covidabc.model.quiz.QuizDAO

class QuizFormsActivity : AppCompatActivity() {

    private var userID = Logger.getUid()
    private lateinit var exitFormsButton: Button
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.quiz_forms)
        setViews()
    }

    private fun setViews() {
        exitFormsButton = findViewById(R.id.exit_forms_btn)
        webView = findViewById(R.id.web_view)

        webView.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView?, url: String?) {
                val script = "document.getElementsByClassName('freebirdFormviewerViewResponseConfirmContentContainer').length > 0"

                webView.evaluateJavascript(script) { value ->
                    if (value != null && value == "true") {
                        QuizDAO.setUserAnswer(userID, object : FirestoreDatabaseOperationListener<Boolean> {
                            override fun onCompleted(sucess: Boolean) {
                                Toast.makeText(applicationContext, getString(R.string.answer_sucess), Toast.LENGTH_SHORT).show()
                                setResult(App.FORMS_SUCCESS)
                                finish()
                            }
                        })
                    }
                }

            }
        }
        webView.settings.apply {
            javaScriptEnabled = true
            displayZoomControls = true
        }
        webView.loadUrl(getString(R.string.forms_url))
    }

}


