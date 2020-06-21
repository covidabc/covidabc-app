package com.ufabc.covidabc

import android.app.Application
import android.content.Context

class App : Application() {

    companion object {
        lateinit var appContext: Context
            private set

        const val FAQ_EXTRA = "faq_extra"
    }


    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext
    }
}