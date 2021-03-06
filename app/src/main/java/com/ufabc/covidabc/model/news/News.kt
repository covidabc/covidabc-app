package com.ufabc.covidabc.model.news


import com.google.firebase.firestore.Exclude
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class News: Serializable {

    private lateinit var title: String
    private lateinit var imageURL: String
    private lateinit var newsURL: String
    private lateinit var source: String
    private lateinit var date: Date
    private lateinit var timeStr: String
    private lateinit var refPath : String
    constructor()

    constructor(title: String, imageURL: String, newsURL: String, source: String) {
        this.title = title
        this.imageURL = imageURL
        this.newsURL = newsURL
    }

    fun getTitle() = this.title

    fun getSource() = "Fonte: " + this.source

    fun getImageURL() = this.imageURL

    fun getNewsURL() = this.newsURL

    fun getDate() = this.date

    fun getTimeStr() = this.timeStr

    fun getFormatedDate(): String {
        val pattern = "dd/MM"
        val time = this.getTimeStr()
        val day = SimpleDateFormat(pattern, Locale.getDefault()).format(this.getDate())

        if (time == "00h00" || time == "-")
            return day

        return "$day às $time"
    }

    @Exclude
    fun getRefPath() : String = this.refPath

    fun setRefPath(refPath: String) {
        this.refPath = refPath
    }
}