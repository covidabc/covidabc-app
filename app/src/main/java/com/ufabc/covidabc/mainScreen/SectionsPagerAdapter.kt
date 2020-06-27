package com.ufabc.covidabc.mainScreen

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ufabc.covidabc.R
import com.ufabc.covidabc.mainScreen.categories.Event.EventFragment
import com.ufabc.covidabc.mainScreen.categories.FAQ.FAQFragment
import com.ufabc.covidabc.mainScreen.categories.News.NewsFragment
import com.ufabc.covidabc.mainScreen.categories.Quiz.QuizFragment

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