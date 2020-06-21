package com.ufabc.covidabc.model

import java.io.Serializable

class FAQ : Serializable {

    private lateinit var question: String

    private lateinit var answer: String

    constructor()

    constructor(question: String, answer: String) {
        this.question = question
        this.answer = answer
    }

    fun getQuestion() : String = question

    fun getAnswer() : String = answer
}