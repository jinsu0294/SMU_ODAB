package com.example.smu_quiz_2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.example.smu_quiz_2.adapter.FolderAdapter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_userfolder.*

class UserFolderActivity : AppCompatActivity(){

    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userfolder)

        //로그인 되어있는지 확인
        auth = FirebaseAuth.getInstance()
        if(auth.currentUser?.email==null){
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            //통신 :
            // 사용자 검색을 해서 이미 등록되있는 사용자라면
            // 로그인만 해주고
            // 맨처음이라면 auth.currentUser!!.email 를 사용자 등록
        }

        // 통신 : 이메일로 폴더조회
        // 이메일 넘겨주면 이메일에 해당되는 폴더 다 보내줌
        // 그걸 사용자 폴더에 다 저장 시켜주고 오답 어댑터에서 불러와주면 될듯

        checkFolder()

        val user = application as User

        // 리사이클러뷰 adapter 설정
        val mAdapter = FolderAdapter(this, user.folderList)
        rvFolderRecyclerview.adapter = mAdapter

        // 레아아웃 매니저 절성
        val lm = androidx.recyclerview.widget.LinearLayoutManager(this)
        rvFolderRecyclerview.layoutManager = lm
        rvFolderRecyclerview.setHasFixedSize(true)


        tvId.text = auth.currentUser?.email

        // result code를 RESULT_OK로 설정(Singin Activity finish() 하려고
        setResult(Activity.RESULT_OK)
        Log.e("user folder", "success")


        // 추가 버튼 리스너
        btnAdd.setOnClickListener {
            val intent = Intent(this,FolderAddActivity::class.java)
            startActivityForResult(intent, SELECT_ADD)
        }
        //logout
        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    // result code -> RESULT_OK면 액티비티 finish()
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(resultCode){
            Activity.RESULT_OK -> {finish()
                Log.e("folder add result","ok")
            }
        }
    }
    fun checkFolder(){
        // 폴더가 없는 경우
        val user = application as User
        val mAdapter = FolderAdapter(this, user.folderList)
        Log.e("폴더리스트 사이즈",mAdapter.itemCount.toString())
        if(mAdapter.itemCount == 0){
            tvNothing.visibility = View.VISIBLE
            rvFolderRecyclerview.visibility = View.INVISIBLE
        }else{  // 폴더가 있는 경우
            tvNothing.visibility = View.GONE
            rvFolderRecyclerview.visibility = View.VISIBLE
        }
    }

    companion object{
        val SELECT_ADD = 500
    }

}