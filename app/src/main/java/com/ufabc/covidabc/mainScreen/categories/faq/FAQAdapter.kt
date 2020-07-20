package com.ufabc.covidabc.mainScreen.categories.faq

import android.content.Intent
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.faq.FAQ
import io.noties.markwon.Markwon

class FAQAdapter(val faq: ArrayList<FAQ>) : RecyclerView.Adapter<FAQAdapter.ViewHolder>() {

    private val markwon: Markwon = Markwon.create(App.appContext)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionText : TextView = itemView.findViewById(R.id.question_text)
        val answerText : TextView = itemView.findViewById(R.id.answer_text)
        val faqCard : CardView = itemView.findViewById(R.id.faq_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_question, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = faq.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.questionText.text = faq[position].getQuestion()
        holder.answerText.movementMethod = LinkMovementMethod.getInstance()
        markwon.setMarkdown(holder.answerText, faq[position].getAnswer())

        holder.faqCard.setOnClickListener {
            val intent = Intent(it.context, FAQDescriptionActivity::class.java)
            intent.putExtra(App.FAQ_EXTRA, faq[position])
            startActivity(it.context, intent, null)
        }
    }
}