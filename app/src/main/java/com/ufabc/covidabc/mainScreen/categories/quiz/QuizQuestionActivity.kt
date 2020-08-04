package com.ufabc.covidabc.mainScreen.categories.quiz

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.quiz.Quiz
import com.ufabc.covidabc.model.quiz.QuizGroup
import kotlinx.android.synthetic.main.activity_quiz_result.*
import kotlinx.android.synthetic.main.dialog_quit.*
import kotlinx.android.synthetic.main.dialog_quiz.*

class QuizQuestionActivity : AppCompatActivity() {

    private lateinit var questionTextView : TextView
    private lateinit var trueButton : Button
    private lateinit var falseButton : Button
    private lateinit var progressBar : ProgressBar

    private lateinit var quizGroup: QuizGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_question)

        this.quizGroup = intent.getSerializableExtra(App.QUIZ_EXTRA) as QuizGroup

        setViews()
        setListeners()
        chooseRandomQuiz()
    }

    override fun onBackPressed() {
        wannaQuit()
    }

    private fun setViews() {
        questionTextView = findViewById(R.id.quiz_question_text_view)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        progressBar = findViewById(R.id.quiz_progress_bar)
    }

    private fun setListeners() {
        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }
    }

    private fun wannaQuit() {
        Dialog(this).apply {
            setCancelable(false)
            setContentView(R.layout.dialog_quit)

            quit_quiz_dialog_button.setOnClickListener {
                dismiss()
                finish()
            }

            continue_quiz_dialog_button.setOnClickListener {
                dismiss()
            }
        }.show()
    }

    private fun checkAnswer(userAnswer : Boolean) {
        val currQuestion = quizGroup.getCurrentQuestion()

        questionTextView.text = ""
        progressBar.progress += 100 / quizGroup.getMaxScore()

        Dialog(this)
            .apply {
                setCancelable(false)
                setContentView(R.layout.dialog_quiz)

                when (quizGroup.provideAnswerAndSkip(userAnswer)) {
                    true -> dialog_title.text = getString(R.string.right_answer)
                    false -> dialog_title.text = getString(R.string.wrong_answer)
                }

                dialog_message.text = currQuestion.getExplanation()

                next_question_button.text = if (quizGroup.isFinished()) "Ver resultado" else "Próxima questão"

                next_question_button.setOnClickListener {
                    dismiss()

                    if (quizGroup.isFinished()) {
                        goToResult()
                        finish()
                    }
                    else {
                        chooseRandomQuiz()
                    }
                }
            }
            .show()
    }

    private fun chooseRandomQuiz() {
        val currQuiz = this.quizGroup.getCurrentQuestion()
        questionTextView.text = currQuiz.getQuestion()
    }

    private fun goToResult() {
        Intent(App.appContext, QuizResultActivity::class.java).apply {
            this.putExtra(App.QUIZ_EXTRA, quizGroup)
            startActivity(this)
        }
    }
}

