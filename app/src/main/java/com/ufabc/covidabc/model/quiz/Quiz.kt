package com.ufabc.covidabc.model.quiz

class Quiz {
    private lateinit var question : String
    private lateinit var explanation : String
    private var isRight : Boolean = true

        constructor()

        constructor (question : String, explanation : String, isRight : Boolean) {
            this.question = question
            this.explanation = explanation
            this.isRight = isRight
        }

        fun getQuestion(): String = this.question

        fun getExplanation(): String = this.explanation

        fun getIsRight(): Boolean = this.isRight
    }
