package com.ufabc.covidabc.ui.main.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.FAQ

class FAQAdapter(val faq: ArrayList<FAQ>, val faqFragment: FAQFragment) : RecyclerView.Adapter<FAQAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionText : TextView = itemView.findViewById(R.id.question_text)
        val answerText : TextView = itemView.findViewById(R.id.answer_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_question, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = faq.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.questionText.text = faq[position].question
        holder.answerText.text = faq[position].answer
    }
}