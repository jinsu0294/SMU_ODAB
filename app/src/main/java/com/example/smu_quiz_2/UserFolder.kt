package com.example.smu_quiz_2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.example.smu_quiz_2.adapter.FolderAdapter
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.userfolder.*

class UserFolder : AppCompatActivity(){

    private lateinit var auth: FirebaseAuth

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.userfolder)

        //로그인 되어있는지 확인
        auth = FirebaseAuth.getInstance()
        if(auth.currentUser?.email==null){
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

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
            val intent = Intent(this,FolderAdd::class.java)
            startActivityForResult(intent,102)
        }
        //logout
        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        ////go home
        ibtnhome.setOnClickListener {
            val intent = Intent(this,UserFolder::class.java)
            startActivity(intent)
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

}