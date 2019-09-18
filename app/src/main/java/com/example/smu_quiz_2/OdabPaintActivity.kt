package com.example.smu_quiz_2


import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_odab_paint.*

import android.graphics.drawable.Drawable
import android.graphics.Bitmap


class OdabPaintActivity : AppCompatActivity() {
    var position: Int = -1
    lateinit var user: User
    var paint = Paint()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_odab_paint)

        user = application as User

        position = intent.getIntExtra("position", -1)

        var myoption = BitmapFactory.Options()
        myoption.inSampleSize=1
//        val mbitmap = BitmapFactory.decodeFile(tempfile.absolutePath,myoption)

        var mbitmap = user.odablist[position].image
        var newbitmap = mbitmap.copy(Bitmap.Config.ARGB_4444, true)
        var canvas = Canvas(newbitmap)
        canvas.drawBitmap(newbitmap, 0f, 0f, paint)
        ivPicture.setImageBitmap(newbitmap)
        ivPicture.rotation=90f
        var drawable = getDrawable(R.drawable.star)
        val starbitmap = getBitmap(drawable)

        ivPicture.setOnTouchListener(View.OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    canvas.drawBitmap(starbitmap, event.x, event.y, null)

                }
            }

            false
        })
    }
    private fun getBitmap(drawable: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }


}

