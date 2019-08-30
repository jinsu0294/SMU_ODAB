package com.example.smu_quiz_2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.example.smu_quiz_2.adapter.QuizFolderAdapter
import kotlinx.android.synthetic.main.activity_quiz_folder.*

class QuizFolderActivity:AppCompatActivity(){

    // 폴더가 있는지 없는지 확인하는 함수
    fun checkFolder(){

        val user = application as User
        val mAdapter = QuizFolderAdapter(this,user.quizlist)

        Log.e("USER QUIZLIST", user.quizlist.size.toString())   // 저장되어있는 quizlist 확인하려는 Log

        // 폴더가 없는 경우
        if(mAdapter.itemCount == 0){
            tvNothing.visibility = View.VISIBLE // 입력값이 없습니다 표시하는 textView 보이게
            rvQuizRecyclerView.visibility = View.GONE   // 리사이클러뷰 안 보이게
            btnAllCheck.visibility = View.GONE  // 전체선택 버튼 없앰(GONE 으로 설정 했음)
        }else{  // 폴더가 있는 경우
            tvNothing.visibility = View.GONE    // 입력값이 없습니다 표시하는 textView 없앰(GONE)
            rvQuizRecyclerView.visibility = View.VISIBLE    // 리라이클러뷰 보이게
            btnAllCheck.visibility = View.VISIBLE   // 전체선택 버튼 보이게
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_folder)

        // 폴더 있는지 없는지 확인 함수 호출
        checkFolder()

        // 임시 데이터 저장 보관소
        val user = application as User

        // adapter 설정
        val mAdapter = QuizFolderAdapter(this,user.quizlist)
        rvQuizRecyclerView.adapter = mAdapter

        // 레아아웃 매니저 설정
        val lm = LinearLayoutManager(this)
        rvQuizRecyclerView.layoutManager = lm
        rvQuizRecyclerView.setHasFixedSize(true)


        // 추가 버튼 리스너
        btnAdd.setOnClickListener {
            // QuizAdd 액티비티로 이동
            val intent = Intent(this,QuizAdd::class.java)
            startActivityForResult(intent,300)
        }

        var click = -1

        // 전체선택 버튼 리스너
        btnAllCheck.setOnClickListener {
            // 홀수 번 클릭 했을 때
            if(click == -1){
                click = 0
                for(i in 0..user.quizlist.size-1){
                    mAdapter.quizList[i].isChecked = true   // 체크박스 선택(O)
                }
            }else{  // 짝수 번 클릭했을 때
                click = -1
                for(i in 0..user.quizlist.size-1){
                    mAdapter.quizList[i].isChecked = false  // 체크박스 선택(X)
                }

            }
            // 바뀐 quizlist를 adpater로 넘기고 다시 adapter설정함 -> 이렇게 하면 checkBox 전체가 선택되고 해제됨
            val mAdapter = QuizFolderAdapter(this,user.quizlist)
            rvQuizRecyclerView.adapter = mAdapter

        }


    }

    // resultCode -> RESULT_OK 이면, 액티비티 종료
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(resultCode){
            Activity.RESULT_OK -> finish()
        }
    }
}