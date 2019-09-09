package com.example.smu_quiz_2

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.content.FileProvider
import com.google.android.gms.common.data.BitmapTeleporter
import kotlinx.android.synthetic.main.activity_picture_choice.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class PictureChoiceActivity: AppCompatActivity(){

    // 권한이 거부되었습니다 토스트메시지 띄우는 함수
    private fun disagree(){
        Log.e("disagree()","!!yes!!")

        Toast.makeText(this,"권한이 거부되었습니다.",Toast.LENGTH_SHORT).show()
    }


    // 앨범에서 이미지 선택
     private fun goToAlbum(){
        Log.e("GoToAlb]um","!!yes!!")

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = ("image/*")
        startActivityForResult(intent, PICK_FROM_ALBUM)
    }

    private fun goToCamera(){
        Log.e("goToCamera()","!!yes!!")

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, PICK_FROM_CAMERA)
    }

    // 이미지 파일 만들기 (사진찍고 크롭을 할 때 필요한것 같은데 못 함)
    private fun createImageFile(): File{
        Log.e("createImageFIle()","!!yes!!")

        val timestamp = SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(Date())
        val imageFileName = "test_${timestamp}_.jpg"    // 이미지 파일 이름 생성
        val storageDir = File(Environment.getExternalStorageDirectory().toString()+"/TEST/")    // 디렉토리 만들기

        if(!storageDir.exists()){   // 디렉토리가 없으면
            storageDir.mkdir()  // mkdir() 호출하여 디렉토리 생성
        }

        val imageFile:File = File(storageDir, imageFileName)
        mCurrentPhotoPath = imageFile.absolutePath


        return imageFile
    }


    // 이미지 자르기
    private fun cropImage(){
        Log.e("cropImage()","!!yes!!")

        val intent = Intent("com.android.camera.action.CROP")


        intent.flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

        intent.setDataAndType(photoUri,"image/*")

        Toast.makeText(this,"용량이 큰 경우 시간이 오래걸릴 수 있습니다.", Toast.LENGTH_SHORT).show()
        intent.putExtra("crop",true)    // 카메라 찍고 바로 자르기 설정

//        intent.putExtra("outputX", 1024)  // crop한 이미지의 x축 크기
//        intent.putExtra("outputY", 1024)  // crop한 이미지의 y축 크기
        intent.putExtra("aspectX", 1)   // crop 박스의 x축 비율
        intent.putExtra("aspectY", 1)   // crop 박스의 y축 비율
        intent.putExtra("scale", true)  // 비율 설정 해놔서 scale true 한 것 같음
        intent.putExtra("return-data",true)



        startActivityForResult(intent, CROP_PHOTO)

    }


    var photoUri: Uri? = null
    var albumUri: Uri? = null
    var croppedFileName: File? = null
    var mCurrentPhotoPath:String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_choice)

        val user = User()

        // 사용자 권한 요청
        var permission = Permission(this)
        permission.checkPer()

        val pref = PreferenceManager.getDefaultSharedPreferences(this)  // 쉐어프리퍼런스 선언
        val result = pref.getBoolean("isPermission", false)     // 사용자 권한 결과 가져옴 // user에 저장
        user.setPermission(result)
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
                    photoUri = data!!.data  // 앨범에서 선택한 사진 Uri
                    Log.e("photoUri",photoUri.toString())
                    cropImage()
                }else{
                    finish()
                }
            }
            PICK_FROM_CAMERA ->{
                Log.e("PICK_FROM_CAMERA","!!yes!!")

                // 인텐트가 null이 아닐 때
                if(data != null){
//                    val imageFile = createImageFile()
//                    photoUri = FileProvider.getUriForFile(this,"com.example.smu_quiz_2.fileProvider",imageFile)
//                    Log.e("pick_from_camer_photouri",photoUri.toString())

//                    cropImage()
                    Log.e("camera Uri", photoUri.toString())
                    val bitmap = data.extras.get("data") as Bitmap
                    user.setphoto(bitmap)
                    setResult(SELECT_PHOTO)
                    finish()
                }else{
                    finish()
                }

            }
            CROP_PHOTO -> {
                if(resultCode == Activity.RESULT_OK) {
                    Log.e("CROP_PHOTO","!!yes!!")

                    val bitmap = data!!.extras.get("data") as Bitmap
                    user.photo = bitmap
                    setResult(SELECT_PHOTO)

//                    val file = File(mCurrentPhotoPath)
//                    if(file.exists()){
//                        file.delete()
//                    }
//
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
        val CROP_PHOTO = 500
    }

}