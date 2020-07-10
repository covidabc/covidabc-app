package com.ufabc.covidabc.mainScreen

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.login.LoginActivity
import com.ufabc.covidabc.mainScreen.categories.event.EventDescriptionActivity

class MainScreenActivity : AppCompatActivity() {

    private lateinit var mainToolbar : Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        setViews()
        setListeners()
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
                R.id.navigation_quiz
            )
        )
        mainToolbar = findViewById(R.id.main_screen_toolbar)
        setupWithNavController(mainToolbar, navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun setListeners() {
        mainToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_login -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> super.onOptionsItemSelected(it)
            }
        }
    }
}