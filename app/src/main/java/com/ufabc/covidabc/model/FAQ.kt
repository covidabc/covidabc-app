package com.ufabc.covidabc.model

import java.io.Serializable

class FAQ : Serializable {

    lateinit var question: String
    lateinit var answer: String

    constructor()

    constructor(question: String, answer: String) {
        this.question = question
        this.answer = answer
    }

}