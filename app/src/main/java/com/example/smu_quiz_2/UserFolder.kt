package com.example.smu_quiz_2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.example.smu_quiz_2.adapter.FolderAdapter
import kotlinx.android.synthetic.main.userfolder.*

class UserFolder : AppCompatActivity(){

    fun checkFolder(){
        // 폴더가 없는 경우
        val user = application as User
        val mAdapter = FolderAdapter(this, user.folderList)
        Log.e("폴더리스트 사이즈",mAdapter.itemCount.toString())
        if(mAdapter.itemCount == 0){
            tvNothing.visibility = View.VISIBLE
            rvFolderRecyclerview.visibility = View.INVISIBLE
        }else{  // 폴더가 있는 경우
            tvNothing.visibility = View.GONE
            rvFolderRecyclerview.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.userfolder)

        checkFolder()

        val user = application as User

        // 리사이클러뷰 adapter 설정
        val mAdapter = FolderAdapter(this, user.folderList)
        rvFolderRecyclerview.adapter = mAdapter

        // 레아아웃 매니저 절성
        val lm = LinearLayoutManager(this)
        rvFolderRecyclerview.layoutManager = lm
        rvFolderRecyclerview.setHasFixedSize(true)

        val result = user.getId()
        tvId.text = "User: ${result}"

        // result code를 RESULT_OK로 설정(Singin Activity finish() 하려고
        setResult(Activity.RESULT_OK)
        Log.e("user folder", "success")


        // 추가 버튼 리스너
        btnAdd.setOnClickListener {
            val intent = Intent(this,FolderAdd::class.java)
            startActivityForResult(intent,102)
        }

    }

    // result code -> RESULT_OK면 액티비티 finish()
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(resultCode){
            Activity.RESULT_OK -> {finish()
                Log.e("folder add result","ok")
            }
        }
    }


}