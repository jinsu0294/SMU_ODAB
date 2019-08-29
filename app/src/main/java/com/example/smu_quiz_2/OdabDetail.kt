package com.example.smu_quiz_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.smu_quiz_2.adapter.OdabFolderAdapter
import kotlinx.android.synthetic.main.odab_detail.*

class OdabDetail:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.odab_detail)

        val user = application as User

        val postion = intent.getIntExtra("position", -1)
        Log.e("아이템 postion",postion.toString())
        if(postion != -1){
            tvUserOdabTitle.text = user.odablist[postion].title
            tvUserTextContents.text = user.odablist[postion].textContents
        }else{
            Toast.makeText(this,getString(R.string.error),Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}