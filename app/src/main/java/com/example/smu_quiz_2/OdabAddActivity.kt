package com.example.smu_quiz_2

import android.annotation.SuppressLint
import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
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
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import retrofit2.http.Url
import java.io.FileOutputStream
import java.io.IOException
import java.net.URLEncoder


class OdabAddActivity : AppCompatActivity() {


    lateinit var storage: FirebaseStorage
    private lateinit var auth: FirebaseAuth
    var smuOdabAPI = SmuOdabAPI()
    var smuInfoRetrofit = smuOdabAPI.smuInfoRetrofit()
    var smuOdabInterface = smuInfoRetrofit.create(SmuOdabInterface::class.java)

    //lateinit var photoFile: File
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

                //firebase에 올리는거
                val storage = FirebaseStorage.getInstance()
                val storageRef = storage.reference
                val OdabRef = storageRef.child("odabimage.jpg")
                val OdabImagesRef = storageRef.child("images/odabimage.jpg")
                OdabRef.name == OdabImagesRef.name // true
                OdabRef.path == OdabImagesRef.path // false
                ivPicture.isDrawingCacheEnabled = true
                ivPicture.buildDrawingCache()

                val bitmap = (ivPicture.drawable as BitmapDrawable).bitmap
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()

                var uploadTask = OdabRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    Log.e("success","실패")
                }.addOnSuccessListener {
                    Log.e("success",OdabImagesRef.downloadUrl.toString())
                    val intent = Intent(this, OdabFolderActivity::class.java)
                    intent.putExtra("Management_id", Management_id)
                    startActivity(intent)
                }
//                val wrong = CreateWrong(
//                    email,
//                    null,
//                    title,
//                    text,
//                    Management_id
//                )
//                Log.e("wrong", wrong.toString())
////                Log.e("Part", part.toString())
//                Log.e("create Odab", wrong.toString())
//
//
//                // TODO:: OK 오답노트생성 createWrong()
//                smuOdabInterface.createWrong(wrong)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe({ result ->
//                        Log.e("create Odab", result.toString())
//                    }, { error ->
//                        error.printStackTrace()
//                        Log.e("creat Odab", "**error**")
//                    }, {
//                    })
//
//                val intent = Intent(this, OdabFolderActivity::class.java)
//                intent.putExtra("Management_id", Management_id)
//                startActivity(intent)
//                setResult(Activity.RESULT_OK)
//                Log.e("Folder Add finish", "yes")
//                finish()
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
    //진수 테스트
    private fun makePart(file : File?):MultipartBody.Part{
        val fileReqBody = RequestBody.create(MediaType.parse("multipart/form-data"),file)
        Log.e("fileReqBody", fileReqBody.toString())
        val part = MultipartBody.Part.createFormData("image_folder", file?.name, fileReqBody)
        Log.e("part", part.toString())
        return part
    }


}

