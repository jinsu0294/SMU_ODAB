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
            }else{  // 입력값이 모두 있을 때
                val folderTitle = etUserFolderTitle.text.toString()

                // TODO:: 폴더생성
                // @POST /folder/list
                // title(폴더 제목), email(사용자 이메일) 보낸다.

                val intent = Intent(this,UserFolderActivity::class.java)
                val user = application as User
                user.add(folderTitle)



                // 인텐트로 title 넘겨줌
                // 통신 되면 여기는 없어져도 됨
                intent.putExtra("title",folderTitle)
                startActivity(intent)
                setResult(Activity.RESULT_OK)
                Log.e("Folder Add finish","yes")
                finish()
            }
        }

    }
}