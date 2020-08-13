package com.ufabc.covidabc.logger

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.ufabc.covidabc.model.logger.NewsViewsLog
import com.ufabc.covidabc.model.logger.NewsViewsLogDAO
import java.security.MessageDigest


object Logger {
    private var uid = sha512(FirebaseInstanceId.getInstance().id)
    private var brigadista_name : String = "sem_nome"
    private var brigadista_email : String = "sem_email"
    private val mAuth : FirebaseAuth = FirebaseAuth.getInstance()

    fun initLogger() {
//        Log.d("LOGGER", uid)
        if (uid.isEmpty()) {
            uid = "missing_hash"
        }


        mAuth.addAuthStateListener {
            updateBrigadistaInfo()
        }
    }


    fun newsAnalytics(newsRefPath: String, tstamp: java.util.Date) {
        Log.d("LOGGER", "NEWS_ANALYTICS")
        val nvl = NewsViewsLog(uid, tstamp, newsRefPath)
        NewsViewsLogDAO.addUserLog(nvl)
    }


    private fun updateBrigadistaInfo() {
        val user = mAuth.currentUser

        if (user != null) {
            brigadista_name = if (user.displayName.isNullOrEmpty()) {
                "sem_nome"
            } else {
                user.displayName.toString()
            }


            brigadista_email = if (user.email.isNullOrEmpty()) {
                "sem_email"
            } else {
                user.email.toString()
            }
        }
    }


    private fun sha512(input: String): String {
        val HEX_CHARS = "0123456789ABCDEF"
            val bytes = MessageDigest
                .getInstance("SHA-512")
                .digest(input.toByteArray())
            val result = StringBuilder(bytes.size * 2)

            bytes.forEach {
                val i = it.toInt()
                result.append(HEX_CHARS[i shr 4 and 0x0f])
                result.append(HEX_CHARS[i and 0x0f])
            }

            return result.toString()
        }
}
