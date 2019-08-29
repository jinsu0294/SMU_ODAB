package com.example.smu_quiz_2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.odab_add.*

class OdabAdd:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.odab_add)

        // 저장 버튼 리스너
        btnSave.setOnClickListener {
            // 입력값이 없는 경우
            if(etUserOdabTitle.text.isEmpty()|| etUserOdabTextContents.text.isEmpty()){
                Toast.makeText(this,getString(R.string.nothing),Toast.LENGTH_SHORT).show()
            }else{  // 입력값이 있는 경우
                val intent = Intent(this, OdabFolder::class.java)

                val user = application as User
                user.addodab(etUserOdabTitle.text.toString(), etUserOdabTextContents.text.toString())
                Log.e("오답추가",user.getodab())

                startActivity(intent)
                Log.e("오답 액티비티 finish","yes")
                setResult(Activity.RESULT_OK)
                finish()
            }
        }

        // 사진 추가 버튼 리스너
        btnPictureAdd.setOnClickListener {
            val intent = Intent(this,PictureChoice::class.java)
            startActivity(intent)
        }

    }
}

