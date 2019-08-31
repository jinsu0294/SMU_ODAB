package com.example.smu_quiz_2

import android.app.Activity
import android.app.VoiceInteractor
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.TokenWatcher
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_odab_add.*
import kotlinx.android.synthetic.main.activity_picture_choice.*
import java.io.File

class PictureChoiceActivity:AppCompatActivity(){

    var isPermission:Boolean = true
//    var tempFile:File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_choice)

        // 사용자 권한 요청
        var permission = Permission(this, isPermission)
        permission.checkPer()

        // 가져오기 버튼 리스너
        btnGetPicture.setOnClickListener {
            setResult(Activity.RESULT_OK)
            // 사용자가 권한을 허락 했을 때
            if (permission.isPermission) {
                // ACTION_GET_CONTENT (앨범호출)
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = ("image/*")   // 이미지만 보이게
                Log.e("앨범열기","YES")
                startActivityForResult(intent, PICK_FROM_ALBUM)

            }else{  // 권한을 거부 했을 때
                Toast.makeText(this,getString(R.string.disagree),Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //super.onActivityResult(requestCode, resultCode, data)
        val user = application as User
        when(requestCode){
            PICK_FROM_ALBUM ->{
                // 이미지 경로를 이용하여 비트맵으로 변경
                val photoUri = data!!.data
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,photoUri)
//           val size = bitmap.height * (1024/bitmap.width)
                val result = Bitmap.createScaledBitmap(bitmap, 100,100,true)
                user.photo = result
                setResult(SELECT_PIC_ALBUM)
                Log.e("PHOTO URI",photoUri.toString())
                finish()
            }
        }

    }

    companion object{
        val PICK_FROM_ALBUM = 2000
        val SELECT_PIC_ALBUM = 4000
    }

}