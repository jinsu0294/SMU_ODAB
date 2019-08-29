package com.example.smu_quiz_2

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.TokenWatcher
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.signin.*
import kotlinx.android.synthetic.main.userfolder.*

class Signin : AppCompatActivity() {

    // 저장되어있는 아이디 있는지 없는지 확인하는 함수
    fun idCheck(){
        // 쉐어프리퍼런스의 저장되어이는 userId를 class User에 저장
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val result = pref.getString("userId",null)
        val user = application as User

        Log.e("User.getUserId()","${result}")

        // result가  null이 아니면 (즉, 저장된 아이디가 있으면)
        if(result != null){
            // 아이디 저장
            user.setId(result)
            // UserFolder 액티비티로 이동
            Toast.makeText(this,"USER: ${result}",Toast.LENGTH_SHORT).show()
            val intent = Intent(this,UserFolder::class.java)
            startActivityForResult(intent,101)
            Log.e("requestCode:101 ->","아이디 있음")
        }

    }

    // 아이디 저장
    fun saveId(userId:String){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()
        editor.putString("userId",userId).apply()
        editor.commit()

        val user = application as User
        user.setId(userId)

        // UserFolder 액티비티로 이동
        val intent = Intent(this, UserFolder::class.java)
        startActivityForResult(intent,101)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin)

        // 저장되어있는 아이디 유무 확인
        idCheck()

        // 로그인 버튼 리스너
        btnLogin.setOnClickListener {
            // 입력값 없을 때
            if(etUserId.text.isEmpty()){
                Toast.makeText(this,getString(R.string.nothing),Toast.LENGTH_SHORT).show()
            }else{  // 입력값 있을 때
                // 아이디 저장
                saveId(etUserId.text.toString())
                // UserFolder 액티비티로 이동
                // intent로 저장된 아이디

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.e("signin", "success")
        when(resultCode){
            Activity.RESULT_OK -> finish()
        }
    }

}
