package com.example.smu_quiz_2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.smu_quiz_2.adapter.QuizFolderAdapter
import kotlinx.android.synthetic.main.activity_quiz_detail.*

class QuizDetail :AppCompatActivity(){

    var smuOdabAPI = SmuOdabAPI()
    var smuInfoRetrofit = smuOdabAPI.smuInfoRetrofit()
    var smuOdabInterface = smuInfoRetrofit.create(SmuOdabInterface::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_detail)

        val user = application as User
        var position = intent.getIntExtra("position", -1)

        // TODO:: 퀴즈상세조회 getQuiz()
        // quiz_id (퀴즈 아이디)를 보냅니다.
        // quiz_id, title, text, choice_1, choice_2, choice_3, choice_4, answer, explain, email 을 받습니다.
        // title, text, choice_1, choice_2, choice_3, choice_4, answer, explain 을 뷰에 보여줍니다.
//
//        tvUserQuizTitle.text = user.quizlist[position].title
//        tvUserQuizContents.text = user.quizlist[position].text
//        tvUserQuiz_1.text = user.quizlist[position].choice_1
//        tvUserQuiz_2.text = user.quizlist[position].choice_2
//        tvUserQuiz_3.text = user.quizlist[position].choice_3
//        tvUserQuiz_4.text = user.quizlist[position].choice_4
//        tvUserQuizExplanation.text = user.quizlist[position].explain

    }
}