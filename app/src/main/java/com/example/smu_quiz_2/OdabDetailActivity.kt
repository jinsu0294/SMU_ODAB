package com.example.smu_quiz_2

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_odab_add.*
import kotlinx.android.synthetic.main.activity_odab_detail.*

class OdabDetailActivity:AppCompatActivity(){

    var smuOdabAPI = SmuOdabAPI()
    var smuInfoRetrofit = smuOdabAPI.smuInfoRetrofit()
    var smuOdabInterface = smuInfoRetrofit.create(SmuOdabInterface::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_odab_detail)

        // TODO:: 오답노트상세조회 getQuiz()
        // wrong_id(오답아이디)를 보내서 내용을 받아옵니다.
        // 오답아이디는 오답리스트에서 선택한 아이템을 이용하여 받아오면 될 듯
        // wrong_id, image, title, text, email, Management_id
        // title, image, text 를 뷰에 보여주면 됩니다.
        // title.text = result.text.toString()
        // text.text = result.text.toString()


        val position = intent.getIntExtra("position", -1)
        Log.e("아이템 postion",position.toString())
        if(position != -1){
          /*  tvUserOdabTitle.text = user.odablist[position].title
            tvUserTextContents.text = user.odablist[position].textContents
//            var mbitmap = user.odablist[position].image
            ivPictureDetail.setImageBitmap(user.odablist[position].image)*/
            ivPictureDetail.rotation=90f

        }else{
            Toast.makeText(this,getString(R.string.error),Toast.LENGTH_SHORT).show()
            finish()
        }
        btnPaint.setOnClickListener {
            val intent = Intent(this, OdabPaintActivity::class.java)
            intent.putExtra("position",position)
            startActivity(intent)
        }
    }
}