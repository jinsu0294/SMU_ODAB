package com.example.smu_quiz_2


import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_picture_choice.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class PictureChoiceActivity: AppCompatActivity(){
    var currentPhotoPath = ""
    lateinit var photouri:Uri

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
                    user.setphotofile(tempfile)
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
                val user = application as User
                user.setphotofile(photoFile)
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
        }
    }

    companion object{
        val PICK_FROM_ALBUM = 200
        val SELECT_PHOTO = 300
        val REQUEST_TAKE_PHOTO = 400
    }


    //비트맵 file형식으로 바꿔줌
    @Throws(IOException::class)
    private fun createFileFromBitmap(bitmap: Bitmap): File {
        val newFile = File(this.getFilesDir(), createImageFile().name)
        val fileOutputStream = FileOutputStream(newFile)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        fileOutputStream.close()
        return newFile
    }

}