package com.example.smu_quiz_2

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_picture_choice.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class PictureChoiceActivity: AppCompatActivity(){
<<<<<<< Updated upstream
    var currentPhotoPath = ""
    lateinit var photouri:Uri
    lateinit var camerafile:File
=======
    var currentPhotoPath:String = ""
>>>>>>> Stashed changes


    // 앨범에서 이미지 선택
     private fun goToAlbum(){
        Log.e("GoToAlb]um","!!yes!!")

        val intent = Intent(Intent.ACTION_PICK)
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE)
        startActivityForResult(intent, PICK_FROM_ALBUM)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_choice)

        val user = application as User

        // 사용자 권한 요청


        val pref = PreferenceManager.getDefaultSharedPreferences(this)  // 쉐어프리퍼런스 선언
        val result = pref.getBoolean("isPermission", false)     // 사용자 권한 결과 가져옴
        user.setPermission(result)  // user에 저장
        val isPer = user.getPermission()    // user에 저장된 사용자 권한의 결과를 isPer 변수로 사용

        // 앨범 버튼 리스너
        btnGetPicture.setOnClickListener {
                goToAlbum()
        }
        // 촬영 버튼 리스너
        btnTakePicture.setOnClickListener {

                dispatchTakePictureIntent()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val user = application as User
        when(requestCode){
            PICK_FROM_ALBUM ->{
                Log.e("PICK_FROM_ALBUM","!!yes!!")

                if(data?.data != null) {  // 인텐트가 null이 아닐 때(사진을 선택했을 때)
                    //사진 URI = photoUri
                    var photoUri = data.data
                    Log.e("PHOTOURI", photoUri.toString())

                    var cursor:Cursor?=null

                    val proj = arrayOf(MediaStore.Images.Media.DATA)
                    assert(photoUri != null)
                    cursor = getContentResolver().query(photoUri, proj, null, null, null)
                    assert(cursor != null)
                    val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    cursor.moveToFirst()
                    var tempfile = File(cursor.getString(column_index))

<<<<<<< Updated upstream
                    Log.e("tempfile.touri",tempfile.toURI().toString())
                    Log.e("tempfile",tempfile.absolutePath)
                    Log.e("tempfile.tourl",tempfile.toURL().toString())
=======
                    Log.e("tempfile : ", tempfile.toString())    //storage/emulated/0/DCIM/Screenshots/Screenshot_20170726-231806.png

>>>>>>> Stashed changes

                    var myoption = BitmapFactory.Options()
                    myoption.inSampleSize=1
                    val mbitmap = BitmapFactory.decodeFile(tempfile.absolutePath,myoption)
                    Log.e("TtempFile",Uri.fromFile(tempfile).toString())    //file:///storage/emulated/0/Pictures/KakaoTalk/1555379132221.jpg
                    Log.e("PhotoPath", tempfile.absolutePath)       ///storage/emulated/0/Pictures/KakaoTalk/1555379132221.jpg
                    Log.e("PhptoFileName", tempfile.name)   //1555379132221.jpg
//

                    user.photoFile = tempfile

                    val mintent = Intent(this,OdabAddActivity::class.java)
                    mintent.putExtra("path",tempfile.absolutePath)
                    user.photo = mbitmap
                    setResult(SELECT_PHOTO)
                    finish()
                }else{
                    finish()
                }
            }
            REQUEST_TAKE_PHOTO ->{
                Log.e("PICK_FROM_CAMERA","!!yes!!")
                    val mbitmap = BitmapFactory.decodeFile(currentPhotoPath)
                    val mintent = Intent(this,OdabPaintActivity::class.java)
                    mintent.putExtra("path",currentPhotoPath)
                    Log.e("tempfile.tourl",currentPhotoPath)
                    user.setphoto(mbitmap)
                    setResult(SELECT_PHOTO)
                    finish()
            }
        }
    }


    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()

                } catch (ex: IOException) {
                  null
                }

<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        packageName,
                        it
                    )
                    photouri = photoURI
                    Log.e("uri",photoFile?.toURL().toString())
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    Log.e("photofile_Path",currentPhotoPath)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        Log.e("date",timeStamp)
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
            Log.e("path",currentPhotoPath)
        }
    }
    companion object{
        val PICK_FROM_ALBUM = 200
        val SELECT_PHOTO = 300
        val REQUEST_TAKE_PHOTO = 400
    }

}