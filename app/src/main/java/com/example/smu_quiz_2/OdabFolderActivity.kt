package com.example.smu_quiz_2

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.example.smu_quiz_2.adapter.OdabFolderAdapter
import kotlinx.android.synthetic.main.activity_userfolder.*

class OdabFolderActivity:AppCompatActivity(){

    fun checkFolder(){
        // view 세팅
        tvId.visibility = View.GONE
        btnLogout.visibility = View.GONE

        val user = application as User
        val mAdapter = OdabFolderAdapter(this, user.odablist)
        Log.e("오답리스트 사이즈",mAdapter.itemCount.toString())

        // 폴더가 없는 경우
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
        setContentView(R.layout.activity_userfolder)

        // TODO:: 오답노트조회
        // @GET /folder/detail_wrong?Management_id=pk
        // Management_id(폴더 아이디)를 넘겨서 오답노트 리스트를 받아옵니다.
        // 액티비티로 돌아올 때마다 리스트를 불러옵니다.

        checkFolder()

        val user = application as User

        // 리사이클러뷰 adapter 설정
        val mAdapter = OdabFolderAdapter(this, user.odablist)
        rvFolderRecyclerview.adapter = mAdapter

        // 레아아웃 매니저 설정
        val lm = androidx.recyclerview.widget.LinearLayoutManager(this)
        rvFolderRecyclerview.layoutManager = lm
        rvFolderRecyclerview.setHasFixedSize(true)

        // 추가 버튼 리스너
        btnAdd.setOnClickListener {
            val intent = Intent(this,OdabAddActivity::class.java)
            startActivityForResult(intent, CHANGE_ACTIVITY)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            finish()
        }

    }

    companion object{
        val CHANGE_ACTIVITY = 100
    }
}