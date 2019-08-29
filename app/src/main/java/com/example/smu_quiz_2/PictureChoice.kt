package com.example.smu_quiz_2

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.media.ImageReader
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import kotlinx.android.synthetic.main.odab_add.*
import kotlinx.android.synthetic.main.picture_choice.*
import java.io.File

class PictureChoice:AppCompatActivity(){

    var isPermission:Boolean = true
    var tempFile:File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.picture_choice)

        // 사용자 권한 요청
        var permission = Permission(this, isPermission)
        permission.checkPer()

        // 가져오기 버튼 리스너
        btnGetPicture.setOnClickListener {
            // 사용자가 권한을 허락 했을 때
            if (permission.isPermission) {
                goToalbum()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 200){
            val photoUri = data!!.data
            Log.e("photoUri",photoUri.toString())

            var cursor: Cursor? = null

            try {

                /*
                 *  Uri 스키마를
                 *  content:/// 에서 file:/// 로  변경한다.
                 */
                val proj = arrayOf(MediaStore.Images.Media.DATA)

                assert(photoUri != null)
                cursor = contentResolver.query(photoUri, proj, null, null, null)

                assert(cursor != null)
                val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

                cursor.moveToFirst()

                tempFile = File(cursor.getString(column_index))

                Log.e("tempFile Uri  ", Uri.fromFile(tempFile).toString())

            } finally {
                cursor?.close()
            }
            setImage()
        }

    }

    fun goToalbum(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, 200)
    }

    fun setImage(){

        val options = BitmapFactory.Options()
        val originalBm = BitmapFactory.decodeFile(tempFile!!.absolutePath,options)

        ivPicture.setImageBitmap(originalBm)

        tempFile = null
    }

}