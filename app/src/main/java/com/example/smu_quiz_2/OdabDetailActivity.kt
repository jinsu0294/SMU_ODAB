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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_odab_detail)

        // TODO:: 오답노트 조회
        // @GET /folder/wrong/{pk}
        // pk -> wrong_id(오답아이디)를 보내서 내용을 받아옵니다.
        // wrong_id, image, title, text, email, Management_id
        // title, image, text 를 뷰에 보여주면 됩니다.

        val user = application as User

        val position = intent.getIntExtra("position", -1)
        Log.e("아이템 postion",position.toString())
        if(position != -1){
            tvUserOdabTitle.text = user.odablist[position].title
            tvUserTextContents.text = user.odablist[position].textContents
            //크기 조절
            var mbitmap = Bitmap.createScaledBitmap(user.odablist[position].image,870,870,true)
            ImageView.setImageBitmap(mbitmap)
            Log.e("dddd",mbitmap.byteCount.toString())
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