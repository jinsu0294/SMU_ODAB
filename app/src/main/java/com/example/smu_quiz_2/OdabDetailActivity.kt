package com.example.smu_quiz_2

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

        val user = application as User

        val position = intent.getIntExtra("position", -1)
        Log.e("아이템 postion",position.toString())
        if(position != -1){
            tvUserOdabTitle.text = user.odablist[position].title
            tvUserTextContents.text = user.odablist[position].textContents
            ImageView.setImageBitmap(user.odablist[position].image)
        }else{
            Toast.makeText(this,getString(R.string.error),Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}