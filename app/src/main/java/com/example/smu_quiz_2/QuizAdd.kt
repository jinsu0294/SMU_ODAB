package com.example.smu_quiz_2

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smu_quiz_2.data_class.CreateQuiz
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_quiz_add.*

class QuizAdd : AppCompatActivity() {

    var smuOdabAPI = SmuOdabAPI()
    var smuInfoRetrofit = smuOdabAPI.smuInfoRetrofit()
    var smuOdabInterface = smuInfoRetrofit.create(SmuOdabInterface::class.java)


    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_add)

        // 스피너에 보여줄 리스트 (1,2,3,4)
        val choice = resources.getStringArray(R.array.choice_array)

        // 스피너 adapter 설정
        val sAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, choice)
        spinner.adapter = sAdapter

        // answer 초기값 -1
        var answer: Int = -1

        // 스피너 리스너
        spinner.onItemSelectedListener =
            object : AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> {
                            answer = 1
                        }
                        1 -> {
                            answer = 2
                        }
                        2 -> {
                            answer = 3
                        }
                        3 -> {
                            answer = 4
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }

        btnSave.setOnClickListener {
            // 입력값이 비어있으면
            if (etUserQuizTitle.text.isEmpty()) {
                Toast.makeText(this, getString(R.string.nothing), Toast.LENGTH_SHORT).show()
            } else {  // 입력값이 있으면
                val email = FirebaseAuth.getInstance().currentUser!!.email.toString()
                val title = etUserQuizTitle.text.toString()
                val text = etUserQuizContents.text.toString()
                val choice_1 = etUserQuiz_1.text.toString()
                val choice_2 = etUserQuiz_2.text.toString()
                val choice_3 = etUserQuiz_3.text.toString()
                val choice_4 = etUserQuiz_4.text.toString()
                val answer = answer // answer는 스피너로 결정된 answer로 값이 결정 됨
                val explain = etUserQuizExplanation.text.toString()
                var Management_id = intent.getIntExtra("Management_id",0)
                var isChecked = false   // isChecked는 체크박스의 체크상태 의미( false == 선택X , true == 선택O )

                // TODO:: OK 퀴즈 생성
                // @POST /folder/problem
                // quiz_id, email, title, text, choice_1, choice_2, choice_3, choice_4, answer, explain, Management_id 를 넘겨줍니다.
                // (quiz_id 는 빈 상태로 보냅니다.)
                // user의 addQuiz()함수로 quizList에 저장

                var quiz = CreateQuiz(
                    email,
                    title,
                    text,
                    choice_1,
                    choice_2,
                    choice_3,
                    choice_4,
                    answer,
                    explain,
                    Management_id
                )
                smuOdabInterface.createQuiz(quiz)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ result ->
                    }, { error ->
                        error.printStackTrace()
                    }, {
                    })
                // QuizFolderActivity 액티비티로 전환하고 액티비티 종료( finish() )
                val intent = Intent(this, QuizFolderActivity::class.java)
                intent.putExtra("Management_id",Management_id)
                startActivity(intent)
                setResult(Activity.RESULT_OK)
                finish()
            }
        }

    }

}