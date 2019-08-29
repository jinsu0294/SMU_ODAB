package com.example.smu_quiz_2

import android.app.Activity
import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_folder_add.*

class FolderAddActivity:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folder_add)

        // ok 버튼 리스
        btnOk.setOnClickListener {
            // 입력값이 없을 때
            if(etUserFolderTitle.text.isEmpty()){
                Toast.makeText(this,getString(R.string.nothing),Toast.LENGTH_SHORT).show()
            }else{  // 입력값이 있을 때
                val folderTitle = etUserFolderTitle.text.toString()
                val intent = Intent(this,UserFolderActivity::class.java)

                // user의 folder list에 result 추가
                val user = application as User
                user.add(folderTitle)

                // 인텐트로 folderTitle 넘겨줌
                intent.putExtra("folderTitle",folderTitle)
                startActivity(intent)
                setResult(Activity.RESULT_OK)
                Log.e("Folder Add finish","yes")
                finish()
            }
        }

    }
}