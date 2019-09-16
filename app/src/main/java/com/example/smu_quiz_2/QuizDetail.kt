package com.example.smu_quiz_2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.smu_quiz_2.adapter.QuizFolderAdapter
import kotlinx.android.synthetic.main.activity_quiz_detail.*

class QuizDetail :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_detail)

        val user = application as User
        var position = intent.getIntExtra("position", -1)

        tvUserQuizTitle.text = user.quizlist[position].title
        tvUserQuizContents.text = user.quizlist[position].text
        tvUserQuiz_1.text = user.quizlist[position].choice_1
        tvUserQuiz_2.text = user.quizlist[position].choice_2
        tvUserQuiz_3.text = user.quizlist[position].choice_3
        tvUserQuiz_4.text = user.quizlist[position].choice_4
        tvUserQuizExplanation.text = user.quizlist[position].explain

    }
}