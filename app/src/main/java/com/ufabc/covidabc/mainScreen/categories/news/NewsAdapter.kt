package com.ufabc.covidabc.mainScreen.categories.news

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.news.News

class NewsAdapter(private val options: FirestorePagingOptions<News>):
    FirestorePagingAdapter<News, NewsAdapter.ViewHolder>(options) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var newsImage : ImageView = itemView.findViewById(R.id.news_image_view)
        var newsTitle : TextView = itemView.findViewById(R.id.news_title_text_view)
        var newsSourceTextView : TextView = itemView.findViewById(R.id.source_text_view)
        var datetimeTextView : TextView = itemView.findViewById(R.id.datetime_text_view)
        var newsBody : RelativeLayout = itemView.findViewById(R.id.news_body)
        var newsProgressBar : ProgressBar = itemView.findViewById(R.id.news_progress_bar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_news, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: News) {
        model.apply {
            holder.newsTitle.text = this.getTitle()
            holder.newsSourceTextView.text =  this.getSource()
            holder.datetimeTextView.text = this.getFormatedDate()

            Glide.with(App.appContext)
                .load(Uri.parse(this.getImageURL()))
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?,
                                              target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        holder.newsProgressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?,
                                                 dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        holder.newsProgressBar.visibility = View.GONE
                        return false
                    }
                })
                .into(holder.newsImage)

            holder.newsBody.setOnClickListener {
                openNewTabWindow(this.getNewsURL())
            }
        }
    }

    private fun openNewTabWindow(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        App.appContext.startActivity(browserIntent)
    }
}