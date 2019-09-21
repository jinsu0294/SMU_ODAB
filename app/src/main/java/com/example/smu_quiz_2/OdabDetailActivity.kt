package com.example.smu_quiz_2

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_odab_add.*
import kotlinx.android.synthetic.main.activity_odab_detail.*
import javax.net.ssl.ManagerFactoryParameters
import kotlin.coroutines.Continuation

class OdabDetailActivity:AppCompatActivity(){

    var smuOdabAPI = SmuOdabAPI()
    var smuInfoRetrofit = smuOdabAPI.smuInfoRetrofit()
    var smuOdabInterface = smuInfoRetrofit.create(SmuOdabInterface::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_odab_detail)

        val user = application as User

        val wrongId = intent.getIntExtra("wrong_id",-1)
        Log.e("OdabDetail_WrongId", wrongId.toString())



//        Log.e("Storage",ref.toString())

//        val url = ref.downloadUrl
//        Log.e("downloadUrl",url.)

        // TODO:: 오답노트상세조회 getWrongDetail()
        smuOdabInterface.getWrongDetail(wrongId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                Log.e("list", list.toString())
                tvUserOdabTitle.text = list.title
                Glide.with(this).load(list.image).into(ivPictureDetail)
//                ivPictureDetail.setImageURL(list.image)
//                ivPictureDetail.setImageURI(null)
                tvUserTextContents.text = list.text
                Log.e("123123", list.image.toString())
            }, { error ->
                error.printStackTrace()
                Log.e("123123", "false")
            }, {
                Log.e("getWrongDetail: ","complete")
            })
        val position = intent.getIntExtra("position", -1)
        Log.e("아이템 postion",position.toString())

        if(position != -1){
          /*  tvUserOdabTitle.text = user.odablist[position].title
            tvUserTextContents.text = user.odablist[position].textContents
//            var mbitmap = user.odablist[position].image
            ivPictureDetail.setImageBitmap(user.odablist[position].image)*/
            ivPictureDetail.rotation=90f

        }else{
            Toast.makeText(this,getString(R.string.error),Toast.LENGTH_SHORT).show()
            finish()
        }

        btnPaint.visibility= View.INVISIBLE
//        btnPaint.setOnClickListener {
//            val intent = Intent(this, OdabPaintActivity::class.java)
//            intent.putExtra("position",position)
//            startActivity(intent)
//        }
    }
}