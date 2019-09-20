package com.example.smu_quiz_2

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.smu_quiz_2.data_class.CreateFolder
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_folder_add.*

class FolderAddActivity:AppCompatActivity(){

    private lateinit var auth: FirebaseAuth

    var smuOdabAPI = SmuOdabAPI()
    var smuInfoRetrofit = smuOdabAPI.smuInfoRetrofit()
    var smuOdabInterface = smuInfoRetrofit.create(SmuOdabInterface::class.java)

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folder_add)

        // 사용자 이메일 받아오기
        auth = FirebaseAuth.getInstance()
        val email =  auth.currentUser!!.email.toString()

        // ok 버튼 리스
        btnOk.setOnClickListener {
            // 입력값이 없을 때
            if(etUserFolderTitle.text.isEmpty()){
                Toast.makeText(this,getString(R.string.nothing),Toast.LENGTH_SHORT).show()
            }else{  // 입력값이 모두 있을 때
                val folderTitle = etUserFolderTitle.text.toString()

                // TODO:: OK 폴더생성 createFolder()
                smuOdabInterface.createFolder(CreateFolder(folderTitle, email))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ result->
                    }, { error ->
                        error.printStackTrace()
                    }, {
                    })

                val intent = Intent(this,UserFolderActivity::class.java)

                startActivity(intent)
                setResult(Activity.RESULT_OK)
                Log.e("Folder Add finish","yes")
                finish()
            }
        }

    }
}