package com.ufabc.covidabc

import android.app.Application
import android.content.Context
import com.ufabc.covidabc.logger.Logger

class App : Application() {

    companion object {
        lateinit var appContext: Context
            private set

        const val IS_LAT_LONG_AVAILABLE_EXTRA = "islatlong_extra"
        const val FAQ_EXTRA = "faq_extra"
        const val EVENT_EXTRA = "event_extra"
        const val QUIZ_EXTRA = "quiz_extra"
        const val LONGITUDE_EXTRA = "longitude_extra"
        const val LATITUDE_EXTRA = "latitude_extra"
        const val ADDRESS_EXTRA = "address_extra"
        const val INVENTORY_EXTRA = "inventory_extra"

        const val WEBSITE_URL = "http://ufabc.net.br/covidabc"
        const val APP_VERSION = "v1"
    }


    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext
        Logger.initLogger()
    }
}