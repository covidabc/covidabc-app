package com.ufabc.covidabc.mainScreen

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.ufabc.covidabc.R

class MainActivity : AppCompatActivity() {

    lateinit var viewPager: ViewPager
    lateinit var tabs: TabLayout
    lateinit var sectionsPagerAdapter: SectionsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setViews()
    }

    private fun setViews() {
        sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)

        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        tabs = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }
}