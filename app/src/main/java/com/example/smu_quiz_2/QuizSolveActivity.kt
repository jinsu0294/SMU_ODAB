package com.example.smu_quiz_2

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.example.smu_quiz_2.data_class.Quiz
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_quiz_solve.*

class QuizSolveActivity : AppCompatActivity() {

    var smuOdabAPI = SmuOdabAPI()
    var smuInfoRetrofit = smuOdabAPI.smuInfoRetrofit()
    var smuOdabInterface: SmuOdabInterface = smuInfoRetrofit.create(SmuOdabInterface::class.java)
    var quiz = arrayListOf<Quiz>()
    var index = 0
    var myanswer =0
    var choice = mutableListOf<Choice>()
    var choicetv = mutableListOf<Choicetv>()
    var explain = false

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_solve)

        choice.add(Choice(ivcorrect1))
        choice.add(Choice(ivcorrect2))
        choice.add(Choice(ivcorrect3))
        choice.add(Choice(ivcorrect4))
        choicetv.add(Choicetv(tvUserQuiz_1))
        choicetv.add(Choicetv(tvUserQuiz_2))
        choicetv.add(Choicetv(tvUserQuiz_3))
        choicetv.add(Choicetv(tvUserQuiz_4))

        val quizId = intent.getIntegerArrayListExtra("quizId")

        Log.e("23423400", quizId.toString())
        smuOdabInterface.getSelectQuizList(quizId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                for (lists in list) {
                    quiz.add(lists)
                }
                Log.e("23423400", quiz.toString())

                setQuiz(index)
            }, { error ->
                error.printStackTrace()
            }, {
            })

        for(i in 0..3){
            choice[i].ivChoice.setImageDrawable(getDrawable(R.drawable.uncorrect))
        }
        tvUserQuizExplanation.visibility = View.INVISIBLE

        tvExplanation.setOnClickListener {
            if(!explain){
                explain=true
                tvUserQuizExplanation.visibility = View.VISIBLE
            }else{
                explain=false
                tvUserQuizExplanation.visibility = View.INVISIBLE
            }

        }
        for(i in 0..3){
            choicetv[i].tvChoice.setOnClickListener {
                myanswer = i
                if(myanswer==quiz[index].answer){
                    choice[i].ivChoice.setImageDrawable(getDrawable(R.drawable.correct))
                }
            }
        }


        btnPrev.setOnClickListener {
            if (index-1 < 0) {
                Toast.makeText(this, "이전 문제가 없습니다.", Toast.LENGTH_SHORT).show()
            } else {
                CorrectInit()
                index--
                setQuiz(index)
            }
        }

        btnNext.setOnClickListener {

            if (index+1  > quiz.size) {
                Toast.makeText(this, "다음 문제가 없습니다.", Toast.LENGTH_SHORT).show()
            } else {
                CorrectInit()
                index++
                setQuiz(index)
            }
        }

    }
    fun CorrectInit(){
        for(i in 0..3){
            choice[i].ivChoice.setImageDrawable(getDrawable(R.drawable.uncorrect))
        }
        myanswer=0
    }

    fun setQuiz(index: Int) {
        tvUserQuizTitle.text = quiz[index].title
        tvUserQuizContents.text = quiz[index].text
        tvUserQuiz_1.text = quiz[index].choice_1
        tvUserQuiz_2.text = quiz[index].choice_2
        tvUserQuiz_3.text = quiz[index].choice_3
        tvUserQuiz_4.text = quiz[index].choice_4
        tvUserQuizExplanation.text = quiz[index].explain
    }
}
