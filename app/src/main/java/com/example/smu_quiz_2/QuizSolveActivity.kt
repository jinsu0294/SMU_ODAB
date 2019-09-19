package com.example.smu_quiz_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.smu_quiz_2.data_class.Quiz
import com.example.smu_quiz_2.data_class.QuizList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_quiz_solve.*

class QuizSolveActivity : AppCompatActivity() {

    var smuOdabAPI = SmuOdabAPI()
    var smuInfoRetrofit = smuOdabAPI.smuInfoRetrofit()
    var smuOdabInterface = smuInfoRetrofit.create(SmuOdabInterface::class.java)

    var quiz = arrayListOf<Quiz>()
    var quizlist = arrayListOf<QuizList>()
    var i =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_solve)

        smuOdabInterface.getQuiz(quizlist[i].quiz_id.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ quiz ->
                Log.e("jinsu", quiz[i].title)
                tvUserQuizTitle.text = quiz[i].title
                tvUserQuizContents.text = quiz[i].text
                tvUserQuiz_1.text = quiz[i].choice_1
                tvUserQuiz_2.text = quiz[i].choice_2
                tvUserQuiz_3.text = quiz[i].choice_3
                tvUserQuiz_4.text = quiz[i].choice_4
                tvUserQuizExplanation.text = quiz[i].explain
            }, { error ->
                error.printStackTrace()
            }, {
            })

        tvUserQuizExplanation.visibility= View.INVISIBLE
        tvExplanation.setOnClickListener {
            tvUserQuizExplanation.visibility= View.VISIBLE
        }

        btnPrev.setOnClickListener {
            if(i-1<0){
                Toast.makeText(this,"이전 문제가 없습니다.",Toast.LENGTH_SHORT).show()
            }else{
                smuOdabInterface.getQuiz(quizlist[i-1].quiz_id.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ quiz ->
                        Log.e("jinsu", quiz[i-1].title)
                        tvUserQuizTitle.text = quiz[i-1].title
                        tvUserQuizContents.text = quiz[i-1].text
                        tvUserQuiz_1.text = quiz[i-1].choice_1
                        tvUserQuiz_2.text = quiz[i-1].choice_2
                        tvUserQuiz_3.text = quiz[i-1].choice_3
                        tvUserQuiz_4.text = quiz[i-1].choice_4
                        tvUserQuizExplanation.text = quiz[i-1].explain
                    }, { error ->
                        error.printStackTrace()
                    }, {
                    })
            }
        }

        btnNext.setOnClickListener {
            if(i+1<quiz.size){
                Toast.makeText(this,"다음 문제가 없습니다.",Toast.LENGTH_SHORT).show()
            }else{
                smuOdabInterface.getQuiz(quizlist[i+1].quiz_id.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ quiz ->
                        Log.e("jinsu", quiz[i+1].title)
                        tvUserQuizTitle.text = quiz[i+1].title
                        tvUserQuizContents.text = quiz[i+1].text
                        tvUserQuiz_1.text = quiz[i+1].choice_1
                        tvUserQuiz_2.text = quiz[i+1].choice_2
                        tvUserQuiz_3.text = quiz[i+1].choice_3
                        tvUserQuiz_4.text = quiz[i+1].choice_4
                        tvUserQuizExplanation.text = quiz[i+1].explain
                    }, { error ->
                        error.printStackTrace()
                    }, {
                    })
            }
        }

    }
}
