package com.example.smu_quiz_2

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_picture_choice.*

class PictureChoiceActivity:AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_choice)

        val user = User()

        // 사용자 권한 요청
        var permission = Permission(this)
        permission.checkPer()

        // 쉐어프리퍼런스 선언
        val pref = PreferenceManager.getDefaultSharedPreferences(this)

        // 사용자 권한 결과 가져와서 user에 저장
        val isPermission = pref.getBoolean("isPermission", false)
        user.setPermission(isPermission)

        // user에 저장한 사용자 권한 결과를 resultPer 변수로 사용
        val resultPer = user.getPermission()

        // 가져오기 버튼 리스너
        btnGetPicture.setOnClickListener {
            // 사용자가 권한 허락
            if (resultPer == true) {
                // ACTION_GET_CONTENT (앨범호출)
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = ("image/*")   // 이미지만 보이게하는 설정
                Log.e("앨범열기","YES")
                startActivityForResult(intent, PICK_FROM_ALBUM)
            }else{  // 권한 거부
                Toast.makeText(this,getString(R.string.disagree),Toast.LENGTH_SHORT).show()
            }
        }
        // 촬영 버튼 리스너
        btnTakePicture.setOnClickListener {
            // 사용자가 권한 허락
            if(resultPer == true){
                // 카메라 호출
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                Log.e("카메라열기","!!yes!!")
                startActivityForResult(intent, PICK_FROM_CAMERA)
            }else{ // 권한 거부
                Toast.makeText(this,getString(R.string.disagree),Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val user = application as User
        when(requestCode){
            PICK_FROM_ALBUM ->{
                // 이미지 경로를 이용하여 비트맵으로 변경
                // 인텐트가 null이 아닐 때(사진을 선택했을 때)
                if(data != null) {
                    val photoUri = data!!.data
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, photoUri)
//           val size = bitmap.height * (1024/bitmap.width)
                    val result = Bitmap.createScaledBitmap(bitmap, 100, 100, true)
                    user.photo = result
                    setResult(SELECT_PIC_ALBUM)
                    Log.e("PHOTO URI", photoUri.toString())
                    finish()
                }else{
                    finish()
                }
            }
            PICK_FROM_CAMERA ->{    //
                Log.e("init camera","!!yes!!")
                // 인텐트가 null이 아닐 때
                if(data != null){
                    val bitmap = data.extras.get("data") as Bitmap
                    user.setphoto(bitmap)
                    setResult(SELECT_PIC_ALBUM)
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
        when(resultCode){
            Activity.RESULT_OK -> finish()
        }

    }

    companion object{
        val PICK_FROM_ALBUM = 200
        val SELECT_PIC_ALBUM = 300
        val PICK_FROM_CAMERA = 400
    }

}