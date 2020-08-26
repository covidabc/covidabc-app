package com.ufabc.covidabc.mainScreen.categories.quiz


import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.ufabc.covidabc.R
import java.security.AccessController.getContext


class QuizFormsActivity : AppCompatActivity() {

    private lateinit var exit_forms_btn: Button
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.quiz_forms)
        setViews()
        setListeners()

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

        val htmlWebView = findViewById<View>(R.id.webView) as WebView
        htmlWebView.webViewClient = CustomWebViewClient()
        val webSetting = htmlWebView.settings
        webSetting.javaScriptEnabled = true
        webSetting.displayZoomControls = true
        htmlWebView.loadUrl(getString(R.string.forms_url))

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        webView.webViewClient = object : WebViewClient()
        {
            @RequiresApi(Build.VERSION_CODES.KITKAT)
            override fun onPageFinished(view: WebView?, url: String?) {
                webView.evaluateJavascript("document.getElementsByClassName('freebirdFormviewerViewResponseConfirmContentContainer').length > 0") {
                    val done = it == "true"
                }
            }
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webView.addJavascriptInterface(WebViewInterface(getContext()), WEBVIEW_INTERFACE_NAME)
        }
    }

    private class WebViewInterface internal constructor(var mContext: Context) {
        @JavascriptInterface
        fun googleFormSubmitted() {
            Log.e("log do kanan", "deu certo")
        }

    }




    }


