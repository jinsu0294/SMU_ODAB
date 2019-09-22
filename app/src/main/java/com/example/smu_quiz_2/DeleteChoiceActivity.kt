package com.example.smu_quiz_2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_picture_choice.*

class DeleteChoiceActivity :AppCompatActivity(){

    var smuOdabAPI = SmuOdabAPI()
    var smuInfoRetrofit = smuOdabAPI.smuInfoRetrofit()
    var smuOdabInterface = smuInfoRetrofit.create(SmuOdabInterface::class.java)


    fun deleteFolder(id: Int){
        // TODO:: 폴더 삭제 delteFolder(id)
//            smuOdabInterface.deleteFolder(id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ result ->
//                    Log.d("Result", "deleteFolder:$result")
//                }, { error ->
//                    error.printStackTrace()
//                    Log.d("Result", "error::deleteFolder")
//                }, { Log.d("Result", "complete::deleteFolder") })
    }

    fun deleteOdab(id: Int){
        // TODO:: 오답노트 삭제 deleteWrong(id)
//            smuOdabInterface.deleteWrong(id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ result ->
//                    Log.d("Result", "deleteOdab:$result")
//                }, { error ->
//                    error.printStackTrace()
//                    Log.d("Result", "error::deleteOdab")
//                }, { Log.d("Result", "complete::deleteOdab") })
    }

    fun delelteQuiz(id: Int){
        // TODO:: 퀴즈 삭제 deleteQuiz(id)
//        smuOdabInterface.deleteWrong(id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ result ->
//                    Log.d("Result", "delelteQuiz:$result")
//                }, { error ->
//                    error.printStackTrace()
//                    Log.d("Result", "error::delelteQuiz")
//                }, { Log.d("Result", "complete::delelteQuiz") })
    }

    // 버튼 backgroundColor, textColor 바꾸기
    private fun settingButton(button: Button){
        button.setBackground(getDrawable(R.drawable.round_fill_white))
        button.setTextColor(getColor(R.color.darkPurple))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_choice)

        btnTakePicture.text = "삭제하기"
        btnGetPicture.text = "취소하기"

        val request = intent.getStringExtra("request")
        val folderId = intent.getIntExtra("Management_id",-1)
        val wrongId = intent.getIntExtra("wrong_id",-1)
        val quizId = intent.getIntExtra("quiz_id",-1)


        if(request == "DeleteFolder"){      // 폴더 삭제
            // 삭제하기 버튼 리스너
            btnTakePicture.setOnClickListener{
                settingButton(btnTakePicture)
                deleteFolder(folderId)
                finish()
            }
        } else if(request == "DeleteOdab"){     // 오답삭제
            btnTakePicture.setOnClickListener {
                settingButton(btnTakePicture)
                deleteOdab(wrongId)
                finish()
            }

        } else if(request == "DeleteQuiz"){     // 퀴즈 삭제
            btnTakePicture.setOnClickListener {
                settingButton(btnTakePicture)
                delelteQuiz(quizId)
                finish()
            }
        } else{         // error
            Toast.makeText(this,"오류입니다.",Toast.LENGTH_SHORT).show()
        }

        // 취소하기 버튼 리스너
        btnGetPicture.setOnClickListener {
            settingButton(btnGetPicture)
            finish()
        }

    }

}