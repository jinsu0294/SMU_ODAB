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
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_odab_add.*
import java.io.File

class OdabAddActivity:AppCompatActivity(){

    private lateinit var auth: FirebaseAuth
    var smuOdabAPI = SmuOdabAPI()
    var smuInfoRetrofit = smuOdabAPI.smuInfoRetrofit()
    var smuOdabInterface = smuInfoRetrofit.create(SmuOdabInterface::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_odab_add)

        val user = application as User
        // 사용자 이메일 받아오기
        auth = FirebaseAuth.getInstance()
        val email =  auth.currentUser!!.email.toString()


        // 저장 버튼 리스너
        btnSave.setOnClickListener {
            // 입력값이 없는 경우
            if(etUserOdabTitle.text.isEmpty()|| etUserOdabTextContents.text.isEmpty() || user.photo == null){
                Toast.makeText(this,getString(R.string.nothing),Toast.LENGTH_SHORT).show()
            }else{  // 입력값이 모두 있는 경우

                // TODO:: 오답노트생성 createWrong()
                // email(이메일), image(이미지), title(오답제목), text(오답설명), Management_id(폴더아이디) 를 보냅니다.
                // email은 위에서 선언한 email 갖다 쓰면 되고
                // image는 모르겠고..
                // title -> etUserOdabTitle.text.toString()
                // text -> etUserOdabTextContents.text.toString()
                // Maagement_id -> 아마 유저폴더에서 리사이클러뷰에 나타난 아이템 클릭했을 때 저장되는 id로 하면 될 것 같아요.

                val intent = Intent(this, OdabFolderActivity::class.java)
                // user - odab list 추가(title, contents)
                startActivityForResult(intent, BUTTON_SAVE)
            }
        }
        //여기서 사진 권한 물어봐야할것 같은디
        var permission = Permission(this)
        val isPer = user.getPermission()
        // 사진 추가 버튼 리스너
        btnPictureAdd.setOnClickListener {
            permission.checkPer()
            if(isPer == true){
                val intent = Intent(this,PictureChoiceActivity::class.java)
                startActivityForResult(intent, ADD_PICTURE)
            }else{ // 권한 거부
                disagree()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val user = application as User

        // 저장 버튼 눌렀을 때
        when(requestCode){
            BUTTON_SAVE->{
                setResult(Activity.RESULT_OK)
                finish()
            }
        }

        // result code로 RESULT_OK 오면 액티비티 finish()
        when(resultCode){
            Activity.RESULT_OK -> finish()
            // PictureChoiceActivity에서 result Code로 SELECT_PIC_ALBUM 넘어옴
            PictureChoiceActivity.SELECT_PHOTO -> {
                Log.e("IMAGE SET","!!yes!!")
                ivPicture.setImageBitmap(user.photo)
                ivPicture.rotation=90f
                btnPictureAdd.visibility = View.GONE
            }
        }

    }

    companion object{
        val BUTTON_SAVE = 900
        val ADD_PICTURE = 500
    }
    private fun disagree(){
        Log.e("disagree()","!!yes!!")

        Toast.makeText(this,"권한이 거부되었습니다.",Toast.LENGTH_SHORT).show()
    }


}

