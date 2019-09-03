package com.example.smu_quiz_2

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_odab_add.*
import java.io.File

class OdabAddActivity:AppCompatActivity(){
    private var tempFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_odab_add)

        val user = application as User
//
//        val photoUri = user.photoUri
//        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,photoUri)
////           val size = bitmap.height * (1024/bitmap.width)
//        val result = Bitmap.createScaledBitmap(bitmap, 100,100,true)



        // 저장 버튼 리스너
        btnSave.setOnClickListener {
            // 입력값이 없는 경우
            if(etUserOdabTitle.text.isEmpty()|| etUserOdabTextContents.text.isEmpty() || user.photo == null){
                Toast.makeText(this,getString(R.string.nothing),Toast.LENGTH_SHORT).show()
            }else{  // 입력값이 있는 경우
                val user = application as User
                val intent = Intent(this, OdabFolderActivity::class.java)
                // user - odab list 추가(title, contents)
                user.addodab(etUserOdabTitle.text.toString(), etUserOdabTextContents.text.toString(),user.photo)
                startActivityForResult(intent, BUTTON_SAVE)
            }
        }

        // 사진 추가 버튼 리스너
        btnPictureAdd.setOnClickListener {
            val intent = Intent(this,PictureChoiceActivity::class.java)
            startActivityForResult(intent, ADD_PICTURE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val user = application as User
        when(requestCode){
            BUTTON_SAVE->{
                setResult(Activity.RESULT_OK)
                finish()
            }
        }

        when(resultCode){

            Activity.RESULT_OK -> finish()
            // PictureChoiceActivity에서 result Code로 SELECT_PIC_ALBUM 넘어옴
            PictureChoiceActivity.SELECT_PIC_ALBUM -> {
                Log.e("IMAGE SET","!!yes!!")
                ivPicture.setImageBitmap(user.photo)
                btnPictureAdd.visibility = View.GONE
            }
        }

    }

    companion object{
        val BUTTON_SAVE = 900
        val ADD_PICTURE = 500
    }


}

