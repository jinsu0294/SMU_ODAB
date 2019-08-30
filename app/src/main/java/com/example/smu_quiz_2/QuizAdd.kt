package com.example.smu_quiz_2

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_odab_add.*
import kotlinx.android.synthetic.main.activity_quiz_add.*
import kotlinx.android.synthetic.main.activity_quiz_add.btnSave

class QuizAdd :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_add)

        val user = application as User

        // 스피너에 보여줄 리스트 (1,2,3,4)
        val choice = resources.getStringArray(R.array.choice_array)

        // 스피너 adapter 설정
        val sAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, choice)
        spinner.adapter = sAdapter

        // answer 초기값 -1
        var answer:Int =  -1

        // 스피너 리스너
        spinner.onItemSelectedListener = object: AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position){
                    0 -> {
                        answer = 1
                    }
                    1 -> {
                        answer = 2
                    }
                    2 ->{
                        answer = 3
                    }
                    3 ->{
                        answer = 4
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        btnSave.setOnClickListener {
            // 입력값이 비어있으면
            if(etUserQuizTitle.text.isEmpty()){
                Toast.makeText(this,getString(R.string.nothing),Toast.LENGTH_SHORT).show()
            }else{  // 입력값이 있으면
                val title = etUserQuizTitle.text.toString()
                val contents = etUserQuizContents.text.toString()
                val quiz_1 = etUserQuiz_1.text.toString()
                val quiz_2 = etUserQuiz_2.text.toString()
                val quiz_3 = etUserQuiz_3.text.toString()
                val quiz_4 = etUserQuiz_4.text.toString()
                val answer = answer // answer는 스피너로 결정된 answer로 값이 결정 됨
                val explanation = etUserQuizExplanation.text.toString()
                var isChecked = false   // isChecked는 체크박스의 체크상태 의미( false == 선택X , true == 선택O )

                // user의 addQuiz()함수로 quizList에 저장
                user.addQuiz(title,contents,quiz_1,quiz_2, quiz_3, quiz_4,answer,explanation,isChecked)

                // QuizFolderActivity 액티비티로 전환하고 액티비티 종료( finish() )
                val intent = Intent(this,QuizFolderActivity::class.java)
                startActivity(intent)
                setResult(Activity.RESULT_OK)
                finish()
            }
        }

    }

}