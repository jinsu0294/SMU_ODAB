package com.example.smu_quiz_2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.smu_quiz_2.adapter.QuizFolderAdapter
import kotlinx.android.synthetic.main.quiz_detail.*

class QuizDetail :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_detail)

        val user = application as User
        var position = intent.getIntExtra("position", -1)

        tvUserQuizTitle.text = user.quizlist[position].title

    }
}