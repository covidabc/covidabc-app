package com.ufabc.covidabc.model.quiz

import com.google.firebase.database.Exclude
import com.ufabc.covidabc.logger.Logger
import java.io.Serializable

class QuizGroup(private  var quizArray: ArrayList<Quiz>, private var quizSize: Int) : Serializable {

    private var playerID = Logger.getUid()
    private var currQuestion = 0
    private var score = 0

    private var rightAnswered = hashSetOf<Int>()
    private var wrongAnswered = hashSetOf<Int>()

    fun getCurrentQuestion(): Quiz = quizArray[currQuestion]

    fun isFinished(): Boolean = currQuestion >= quizSize

    fun provideAnswerAndSkip(answer: Boolean) : Boolean {
        val isRight = if (answer == this.quizArray[currQuestion].getIsRight()) {
            rightAnswered.add(currQuestion)
            score++
            true
        } else {
            wrongAnswered.add(currQuestion)
            false
        }

        currQuestion++
        return isRight
    }

    fun getScore(): Int = this.score

    fun getMaxScore(): Int = this.quizSize

    fun getRightAnsweredQuestions() = quizArray.filterIndexed { index, _ -> rightAnswered.contains(index) }

    fun getWrongAnsweredQuestions() = quizArray.filterIndexed { index, _ -> wrongAnswered.contains(index) }

    fun getPlayerID() = playerID
}