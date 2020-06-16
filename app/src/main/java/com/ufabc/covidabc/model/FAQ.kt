package com.ufabc.covidabc.model

class FAQ {

    lateinit var question: String
    lateinit var answer: String

    constructor()

    constructor(question: String, answer: String) {
        this.question = question
        this.answer = answer
    }

}