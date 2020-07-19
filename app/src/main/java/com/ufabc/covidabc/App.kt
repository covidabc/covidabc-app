package com.ufabc.covidabc

import android.app.Application
import android.content.Context

class App : Application() {

    companion object {
        lateinit var appContext: Context
            private set

        const val FAQ_EXTRA = "faq_extra"
        const val EVENT_EXTRA = "event_extra"
        const val NEWS_EXTRA = "news_extra"
    }


    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext
    }
}