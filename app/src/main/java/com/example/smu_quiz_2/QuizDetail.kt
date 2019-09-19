package com.example.smu_quiz_2

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_quiz_detail.*


class QuizDetail :AppCompatActivity(){

    var smuOdabAPI = SmuOdabAPI()
    var smuInfoRetrofit = smuOdabAPI.smuInfoRetrofit()
    var smuOdabInterface = smuInfoRetrofit.create(SmuOdabInterface::class.java)


    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_detail)

        var position = intent.getIntExtra("position", -1)
        val  quiz_id= intent.getIntExtra("quiz_id",0)
        Log.e("123123", "quiz_id"+quiz_id.toString())

        // TODO:: 퀴즈상세조회 getQuiz()
        smuOdabInterface.getQuiz(quiz_id.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                tvUserQuizTitle.text = list[0].title
                tvUserQuizContents.text = list[0].text
                tvUserQuiz_1.text = list[0].choice_1
                tvUserQuiz_2.text = list[0].choice_2
                tvUserQuiz_3.text = list[0].choice_3
                tvUserQuiz_4.text = list[0].choice_4
                tvUserQuizExplanation.text = list[0].explain
                Log.e("123123", list.toString())
            }, { error ->
                error.printStackTrace()
                Log.e("123123", "flae")

            }, {
            })
//         quiz_id (퀴즈 아이디)를 보냅니다.
//         quiz_id, title, text, choice_1, choice_2, choice_3, choice_4, answer, explain, email 을 받습니다.
//         title, text, choice_1, choice_2, choice_3, choice_4, answer, explain 을 뷰에 보여줍니다.

//        tvUserQuizTitle.text = user.quizlist[position].title
//        tvUserQuizContents.text = user.quizlist[position].text
//        tvUserQuiz_1.text = user.quizlist[position].choice_1
//        tvUserQuiz_2.text = user.quizlist[position].choice_2
//        tvUserQuiz_3.text = user.quizlist[position].choice_3
//        tvUserQuiz_4.text = user.quizlist[position].choice_4
//        tvUserQuizExplanation.text = user.quizlist[position].explain

    }
}