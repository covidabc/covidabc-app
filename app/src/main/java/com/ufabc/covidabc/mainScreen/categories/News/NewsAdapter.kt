package com.ufabc.covidabc.mainScreen.categories.News

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.News

class NewsAdapter(private val news: ArrayList<News>): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //var newsImage : ImageView = itemView.findViewById(R.id.newsImageView)
        var newsTitle : TextView = itemView.findViewById(R.id.textViewNews)
        var newsTitleAux : TextView = itemView.findViewById(R.id.textViewNewsAux)
        var newsBody : LinearLayout = itemView.findViewById(R.id.news_body)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_news, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = news.size

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        news[position].apply {
            holder.newsTitle.text = this.getTitulo()
            holder.newsTitleAux.text = this.getTituloAuxiliar()
            //holder.newsImage.setImageURI()

            holder.newsBody.setOnClickListener {
                val intent = Intent(it.context, NewsDescriptionActivity::class.java)
                intent.putExtra(App.NEWS_EXTRA, this)
                ContextCompat.startActivity(it.context, intent, null)
            }

        }

    }
}