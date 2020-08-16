package com.ufabc.covidabc.mainScreen

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.login.LoginActivity
import com.ufabc.covidabc.login.MyAccountActivity
import kotlinx.android.synthetic.main.dialog_version.*
import java.util.*

class MainScreenActivity : AppCompatActivity() {

    private lateinit var mainToolbar : Toolbar

    private val mAuth : FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        setViews()
        setListeners()

        verifyAppVersion()
    }

    private fun verifyAppVersion() {
        val currentVersion = App.APP_VERSION.toUpperCase(Locale.ROOT)

        FirebaseFirestore.getInstance().collection("app-info").get()
            .addOnSuccessListener {
                val serverVersion: String = it.map { snapshot ->
                    snapshot["version"].toString()
                }.first().toUpperCase(Locale.ROOT)

                if (currentVersion != serverVersion) {
                    Dialog(this).apply {
                        setCancelable(false)
                        setContentView(R.layout.dialog_version)

                        ok_version_dialog_button.setOnClickListener {
                            dismiss()
                        }
                    }.show()
                }
            }
    }

    private fun setViews() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_news,
                R.id.navigation_quiz,
                R.id.navigation_home,
                R.id.navigation_event,
                R.id.navigation_faq
            )
        )
        mainToolbar = findViewById(R.id.main_screen_toolbar)
        mainToolbar.overflowIcon = ContextCompat.getDrawable(this,R.drawable.ic_menu)
        setupWithNavController(mainToolbar, navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun setListeners() {
        mainToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_login -> {
                    val intent = when(isUserLoggedIn()) {
                        false -> Intent(this, LoginActivity::class.java)
                        true -> Intent(this, MyAccountActivity::class.java)
                    }

                    startActivity(intent)
                }

                R.id.action_about -> openNewTabWindow(App.WEBSITE_URL)

                R.id.action_feedback -> {
                    startActivity(Intent(this, FeedbackActivity::class.java))
                }

                else -> super.onOptionsItemSelected(it)
            }

            true
        }
    }

    private fun isUserLoggedIn() : Boolean = mAuth.currentUser != null

    private fun openNewTabWindow(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        App.appContext.startActivity(browserIntent)
    }
}