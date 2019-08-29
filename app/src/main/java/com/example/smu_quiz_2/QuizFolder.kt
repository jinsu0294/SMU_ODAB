package com.example.smu_quiz_2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.example.smu_quiz_2.adapter.QuizFolderAdapter
import kotlinx.android.synthetic.main.quiz_folder.*

class QuizFolder:AppCompatActivity(){
    fun checkFolder(){

        val user = application as User
        val mAdapter = QuizFolderAdapter(this,user.quizlist)

        Log.e("USER QUIZLIST", user.quizlist.size.toString())

        // 폴더가 없는 경우
        if(mAdapter.itemCount == 0){
            tvNothing.visibility = View.VISIBLE
            rvQuizRecyclerView.visibility = View.GONE
            btnAllCheck.visibility = View.GONE
        }else{  // 폴더가 있는 경우
            tvNothing.visibility = View.GONE
            rvQuizRecyclerView.visibility = View.VISIBLE
            btnAllCheck.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_folder)

        checkFolder()

        val user = application as User

        // adapter 설정
        val mAdapter = QuizFolderAdapter(this,user.quizlist)
        rvQuizRecyclerView.adapter = mAdapter

        // 레아아웃 매니저 설정
        val lm = androidx.recyclerview.widget.LinearLayoutManager(this)
        rvQuizRecyclerView.layoutManager = lm
        rvQuizRecyclerView.setHasFixedSize(true)


        // 추가 버튼 리스너
        btnAdd.setOnClickListener {
            val intent = Intent(this,QuizAdd::class.java)
            startActivityForResult(intent,300)
        }

        // 전체선택 버튼 리스너
        btnAllCheck.setOnClickListener {

        }

    }

    // 액티비티 종료 액티비티
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(resultCode){
            Activity.RESULT_OK -> finish()
        }
    }
}