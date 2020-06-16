package com.ufabc.covidabc.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ufabc.covidabc.R
import com.ufabc.covidabc.ui.main.categories.EventFragment
import com.ufabc.covidabc.ui.main.categories.FAQFragment
import com.ufabc.covidabc.ui.main.categories.NewsFragment
import com.ufabc.covidabc.ui.main.categories.QuizFragment

private val TAB_TITLES = arrayOf(
    R.string.news_fragment_title,
    R.string.quiz_fragment_title,
    R.string.faq_fragment_title,
    R.string.event_fragment_title
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> NewsFragment()
            1 -> QuizFragment()
            2 -> FAQFragment()
            else -> EventFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int = TAB_TITLES.size
}