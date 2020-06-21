package com.ufabc.covidabc.ui.main.categories

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.FAQ
import com.ufabc.covidabc.ui.main.MainActivity

class FAQAdapter(val faq: ArrayList<FAQ>) : RecyclerView.Adapter<FAQAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionText : TextView = itemView.findViewById(R.id.question_text)
        val answerText : TextView = itemView.findViewById(R.id.answer_text)
        val faqCard : RelativeLayout = itemView.findViewById(R.id.faq_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_question, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = faq.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.questionText.text = faq[position].getQuestion()
        holder.answerText.text = faq[position].getAnswer()

        holder.faqCard.setOnClickListener {
            val intent = Intent(it.context, FAQDescriptionActivity::class.java)
            intent.putExtra(App.FAQ_EXTRA, faq[position])
            startActivity(it.context, intent, null)
        }
    }
}