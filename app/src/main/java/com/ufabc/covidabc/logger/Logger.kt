package com.ufabc.covidabc.logger

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.ufabc.covidabc.App
import com.ufabc.covidabc.model.logger.*
import java.security.MessageDigest
import java.util.*


object Logger {
    private lateinit var uid : String
    private var sPName = "uid"
    private var sPDefaultName = "none"

    private var brigadista_name : String = "sem_nome"
    private var brigadista_email : String = "sem_email"
    private val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    val sharedPref = App.appContext.getSharedPreferences("user-sets", Context.MODE_PRIVATE)

    fun initLogger() {
        setUid()

        if (uid.isEmpty()) {
            uid = "missing_hash"
        }

        mAuth.addAuthStateListener {
            updateBrigadistaInfo()
        }
    }

    fun setUid() {
        val savedUid = sharedPref.getString(sPName, sPDefaultName).toString()
        Log.d("LOGSP", savedUid)
        if (savedUid == sPDefaultName) { // nenhum id já foi salvo
            uid = sha512(FirebaseInstanceId.getInstance().id)
            userIDLog(uid, Calendar.getInstance().time)
            with (sharedPref.edit()) {
                putString(sPName, uid)
                commit()
            }
        } else { // existe um id salvo
            uid = savedUid.toString()
        }
    }

    fun newsAnalytics(newsRefPath: String, tstamp: java.util.Date) {
        Log.d("LOGGER", "NEWS_ANALYTICS")
        val nvl = NewsViewsLog(uid, tstamp, newsRefPath)
        NewsViewsLogDAO.addUserLog(nvl)
    }

    fun eventLogInfo(action : String, eventID : String) {
        Log.d("LOGGER", "EVENT")
        val ev = EventLog(brigadista_name, brigadista_email, action,  Calendar.getInstance().time, eventID)
        EventLogDAO.addEventLog(ev)
    }

    fun userIDLog(id: String, date: Date) {
       Log.d("LOGGER", "USER-ID-CREATION")
        val uidObject = UserIDLog(id, date, App.APP_VERSION)

        FirebaseFirestore.getInstance().collection("users-id-log").add(uidObject).addOnCompleteListener {
            Log.d("LOG-USER-ID", it.isSuccessful.toString())
        }
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

    fun getUid(): String = uid
}
