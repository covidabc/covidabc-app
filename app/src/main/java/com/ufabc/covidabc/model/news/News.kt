package com.ufabc.covidabc.model.news


import java.io.Serializable

class News: Serializable {

    private lateinit var title: String
    private lateinit var imgageURL: String
    private lateinit var newsURL: String
    private lateinit var source: String

    constructor()

    constructor(title: String, imageURL: String, newsURL: String, source: String) {
        this.title = title
        this.imgageURL = imageURL
        this.newsURL = newsURL
    }

    fun getTitle() = this.title
    fun getSource() = this.source
    fun getImgageURL() = this.imgageURL
    fun getNewsURL() = this.newsURL
}