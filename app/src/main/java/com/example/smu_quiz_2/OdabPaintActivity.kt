package com.example.smu_quiz_2

import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_odab_paint.*

class OdabPaintActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_odab_paint)

        val user = application as User

        val position = intent.getIntExtra("position",-1)
//        val canvas = Canvas()
//        canvas.setBitmap(user.odablist[position].image)
        ivPicture.setImageBitmap(user.odablist[position].image)


    }
}
