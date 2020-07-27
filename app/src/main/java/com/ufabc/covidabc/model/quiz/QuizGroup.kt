package com.ufabc.covidabc.model.quiz

import java.io.Serializable

class QuizGroup(private  var quizArray: ArrayList<Quiz>, private var quizSize: Int) : Serializable {

    private var currQuestion = 0
    private var score = 0

    fun getCurrentQuestion(): Quiz = quizArray[currQuestion]

    fun isFinished(): Boolean = currQuestion >= quizSize

    fun provideAnswerAndSkip(answer: Boolean) : Boolean {
        if (answer == this.quizArray[currQuestion++].getIsRight()) {
            score++
            return true
        }

        return false
    }

    fun getScore(): Int = this.score

    fun getMaxScore(): Int = this.quizSize
}