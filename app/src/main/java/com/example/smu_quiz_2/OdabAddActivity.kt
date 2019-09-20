package com.example.smu_quiz_2

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.smu_quiz_2.data_class.CreateWrong
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_odab_add.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File

class OdabAddActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    var smuOdabAPI = SmuOdabAPI()
    var smuInfoRetrofit = smuOdabAPI.smuInfoRetrofit()
    var smuOdabInterface = smuInfoRetrofit.create(SmuOdabInterface::class.java)

    lateinit var photoFile: File
    lateinit var bitmapImage: Bitmap

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_odab_add)

        val Management_id = intent.getIntExtra("Management_id", 0)
        Log.e("Odab add FolderId", Management_id.toString())

        val user = application as User
        // 사용자 이메일 받아오기
        auth = FirebaseAuth.getInstance()
        val email = auth.currentUser!!.email.toString()


        // 저장 버튼 리스너
        btnSave.setOnClickListener {
            // 입력값이 없는 경우
            if (etUserOdabTitle.text.isEmpty() || etUserOdabTextContents.text.isEmpty() || user.photo == null) {
                Toast.makeText(this, getString(R.string.nothing), Toast.LENGTH_SHORT).show()
            } else {  // 입력값이 모두 있는 경우
                val title = etUserOdabTitle.text.toString()
                val text = etUserOdabTextContents.text.toString()

                Log.e("Photo", photoFile.toString()) // /storage/.../filename.jpg

//                val file = Uri.fromFile(photoFile)  // file://
//
//                val fileReqBody = RequestBody.create(MediaType.parse("multipart/form-data"), photoFile)
//                val part = MultipartBody.Part.createFormData("image", photoFile.name, fileReqBody
//                ) // okhttp3.MultipartBody$Part@92021f6
//
//                //Create a file object using file path
//                val file2 :File = (photoFile)
//                // Create a request body with file and image media type
//                val fileReqBody2 = RequestBody.create(MediaType.parse("image/*"), file2)
//                // Create MultipartBody.Part using file request-body,file name and part name
//                val part2 = MultipartBody.Part.createFormData("photo",photoFile.name, fileReqBody2)

                val wrong = CreateWrong(
                    email,
                    null,
                    title,
                    text,
                    Management_id
                )
//                Log.e("Part", part.toString())
                Log.e("create Odab", wrong.toString())


                // TODO:: OK 오답노트생성 createWrong()
                smuOdabInterface.createWrong(wrong)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ result ->
                        Log.e("create Odab", result.toString())
                    }, { error ->
                        error.printStackTrace()
                        Log.e("creat Odab", "**error**")
                    }, {
                    })

                val intent = Intent(this, OdabFolderActivity::class.java)
                intent.putExtra("Management_id", Management_id)
                startActivity(intent)
                setResult(Activity.RESULT_OK)
                Log.e("Folder Add finish", "yes")
                finish()
            }
        }

        //여기서 사진 권한 물어봐야할것 같은디
        var permission = Permission(this)
        val pref = PreferenceManager.getDefaultSharedPreferences(this)  // 쉐어프리퍼런스 선언
        val result = pref.getBoolean("isPermission", false)     // 사용자 권한 결과 가져옴
        user.setPermission(result)  // user에 저장
        val isPer = user.getPermission()    // user에 저장된 사용자 권한의 결과를 isPer 변수로 사용
        // 사진 추가 버튼 리스너
        btnPictureAdd.setOnClickListener {
            permission.checkPer()
            if (isPer == true) {
                val intent = Intent(this, PictureChoiceActivity::class.java)
                startActivityForResult(intent, ADD_PICTURE)
            } else { // 권한 거부
                disagree()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val user = application as User

        // result code로 RESULT_OK 오면 액티비티 finish()
        when (resultCode) {
            Activity.RESULT_OK -> finish()
            // PictureChoiceActivity에서 result Code로 SELECT_PIC_ALBUM 넘어옴
            PictureChoiceActivity.SELECT_PHOTO -> {
                Log.e("IMAGE SET", "!!yes!!")
                photoFile = user.photoFile
                bitmapImage = user.photo

                ivPicture.setImageBitmap(user.photo)
                ivPicture.rotation = 90f
                btnPictureAdd.visibility = View.GONE
            }
        }

    }

    companion object {
        val ADD_PICTURE = 500
    }

    private fun disagree() {
        Log.e("disagree()", "!!yes!!")

        Toast.makeText(this, "권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
    }


}

