package com.example.smu_quiz_2

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_picture_choice.*
import java.io.File


class PictureChoiceActivity: AppCompatActivity(){

    // 권한이 거부되었습니다 토스트메시지 띄우는 함수
    private fun disagree(){
        Log.e("disagree()","!!yes!!")

        Toast.makeText(this,"권한이 거부되었습니다.",Toast.LENGTH_SHORT).show()
    }


    // 앨범에서 이미지 선택
     private fun goToAlbum(){
        Log.e("GoToAlb]um","!!yes!!")

        val intent = Intent(Intent.ACTION_PICK)
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE)
        startActivityForResult(intent, PICK_FROM_ALBUM)
    }

    private fun goToCamera(){
        Log.e("goToCamera()","!!yes!!")

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, PICK_FROM_CAMERA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_choice)

        val user = application as User

        // 사용자 권한 요청
        var permission = Permission(this)
        permission.checkPer()

        val pref = PreferenceManager.getDefaultSharedPreferences(this)  // 쉐어프리퍼런스 선언
        val result = pref.getBoolean("isPermission", false)     // 사용자 권한 결과 가져옴
        user.setPermission(result)  // user에 저장
        val isPer = user.getPermission()    // user에 저장된 사용자 권한의 결과를 isPer 변수로 사용

        // 앨범 버튼 리스너
        btnGetPicture.setOnClickListener {
            if (isPer==true) {  // 사용자가 권한 허락
                goToAlbum()
            }else{  // 사용자 권한 거부
                disagree()
            }
        }
        // 촬영 버튼 리스너
        btnTakePicture.setOnClickListener {
            // 사용자가 권한 허락
            if(isPer == true){
                // 카메라 호출
                goToCamera()

            }else{ // 권한 거부
                disagree()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val user = application as User
        when(requestCode){
            PICK_FROM_ALBUM ->{
                Log.e("PICK_FROM_ALBUM","!!yes!!")

                if(data!!.data != null) {  // 인텐트가 null이 아닐 때(사진을 선택했을 때)
                    var photoUri = data.data
                    var cursor:Cursor?=null

                    val proj = arrayOf(MediaStore.Images.Media.DATA)
                    assert(photoUri != null)
                    cursor = getContentResolver().query(photoUri, proj, null, null, null)
                    assert(cursor != null)
                    val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    cursor.moveToFirst()
                    var tempfile = File(cursor.getString(column_index))


                    var myoption = BitmapFactory.Options()
                    myoption.inSampleSize=1
                    val mbitmap = BitmapFactory.decodeFile(tempfile.absolutePath,myoption)

                    user.photo = mbitmap
                    setResult(SELECT_PHOTO)
                    finish()
                }else{
                    finish()
                }
            }
            PICK_FROM_CAMERA ->{
                Log.e("PICK_FROM_CAMERA","!!yes!!")

                // 인텐트가 null이 아닐 때
                if(data != null){

                    //해상도
                    var myoption = BitmapFactory.Options()
                    myoption.inSampleSize=1
                    val mbitmap = BitmapFactory.decodeFile(data.data.toString(),myoption)

                    user.setphoto(mbitmap)
                    setResult(SELECT_PHOTO)
                    finish()
                }else{
                    finish()
                }

            }
            else -> {
                Toast.makeText(applicationContext,"취소되었습니다.",Toast.LENGTH_SHORT).show()
                finish()
            }
        }


    }

    companion object{
        val PICK_FROM_ALBUM = 200
        val SELECT_PHOTO = 300
        val PICK_FROM_CAMERA = 400
    }

}