package com.ufabc.covidabc.mainScreen.categories.Quiz

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ufabc.covidabc.R
import kotlinx.android.synthetic.main.fragment_quiz.*

class QuizFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quiz_enterButton.setOnClickListener {
            val intent = Intent(it.context, QuizQuestionActivity::class.java)
            // start your next activity
            startActivity(intent)
        }
    }



}