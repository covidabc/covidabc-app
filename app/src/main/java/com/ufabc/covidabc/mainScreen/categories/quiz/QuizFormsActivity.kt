package com.ufabc.covidabc.mainScreen.categories.quiz


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ufabc.covidabc.R


class QuizFormsActivity : AppCompatActivity() {

    private lateinit var exit_forms_btn : Button
    private lateinit var webView : WebView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.quiz_forms)
        setViews()
        setListeners()

        val htmlWebView = findViewById<View>(R.id.webView) as WebView
        htmlWebView.webViewClient = CustomWebViewClient()
        val webSetting = htmlWebView.settings
        webSetting.javaScriptEnabled = true
        webSetting.displayZoomControls = true
        htmlWebView.loadUrl(getString(R.string.forms_url))
    }


    private class CustomWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView,
            url: String
        ): Boolean {
            view.loadUrl(url)
            return true
        }
    }


    private fun setViews() {
        exit_forms_btn = findViewById(R.id.exit_forms_btn)
        webView = findViewById(R.id.webView)

    }

    private fun setListeners() {
        exit_forms_btn.setOnClickListener(View.OnClickListener { finish() })
    }



    }

