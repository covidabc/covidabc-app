package com.ufabc.covidabc.model.faq

import java.io.Serializable

class FAQ() : Serializable {

    private lateinit var question: String
    private lateinit var answer: String
    private lateinit var source: String
    private lateinit var sourceURL: String

    fun getQuestion() : String = question

    fun getAnswer() : String = answer.replace( "\\n", "\n" )

    fun getSource() : String = this.source

    fun getSourceURL() : String = this.sourceURL
}