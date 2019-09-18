package com.example.smu_quiz_2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.example.smu_quiz_2.adapter.FolderAdapter
import com.example.smu_quiz_2.data_class.FolderList
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_userfolder.*

class UserFolderActivity : AppCompatActivity(){

    private lateinit var auth: FirebaseAuth
    var email:String? = null
    var smuOdabAPI = SmuOdabAPI()
    var smuInfoRetrofit = smuOdabAPI.smuInfoRetrofit()
    var smuOdabInterface = smuInfoRetrofit.create(SmuOdabInterface::class.java)

    // 통신 결과로 받은 폴더 리스트를 담을 리스트
    var folderList = ArrayList<FolderList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userfolder)

        // 사용자 이메일 받아오기
        auth = FirebaseAuth.getInstance()
        email =  auth.currentUser!!.email.toString()

        //로그인 되어있는지 확인
        if(auth.currentUser?.email==null){  // 사용자 아이디가 없으면 LoginActivity 로 가기
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }else{ // 사용자 아이디가 있으면
            // 상단 텍스트 뷰에 이메일 표시하기
            tvId.text = email

            // TODO:: 이메일로 폴더 조회 getFolderList()
            // 호출이 나중에 됨 돌아버림...ㅠ
            // 근데 잘 받아오고 folderList 에도 잘 들어감
            smuOdabInterface.getFolderList(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list->
                    // i = 0; 받아온 list 만큼 돌면서 folderList 에 요소 추가
                    // 여기서 세팅된 folderList 는 리사이클러뷰 adapter 에 넘겨줄 것이다.
                    for( i in 0..list.size-1){
                        folderList.add(FolderList(list[i].id,list[i].title, email))
                    }
                    Log.e("folderListSize", folderList.size.toString())
                }, { error ->
                    Log.e("123123","false")
                    error.printStackTrace()
                }, {
                    Log.e("123123","complete")
                })

        }

        // 리사이클러뷰 adapter 설정
        val mAdapter = FolderAdapter(this, folderList)
        rvFolderRecyclerview.adapter = mAdapter

        // 레아아웃 매니저 절성
        val lm = androidx.recyclerview.widget.LinearLayoutManager(this)
        rvFolderRecyclerview.layoutManager = lm
        rvFolderRecyclerview.setHasFixedSize(true)

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


//    fun checkFolder(){
//        // 폴더가 없는 경우
//        val user = application as User
//        val mAdapter = FolderAdapter(this, user.folderList)
//        Log.e("폴더리스트 사이즈",mAdapter.itemCount.toString())
//        if(mAdapter.itemCount == 0){
//            tvNothing.visibility = View.VISIBLE
//            rvFolderRecyclerview.visibility = View.INVISIBLE
//        }else{  // 폴더가 있는 경우
//            tvNothing.visibility = View.GONE
//            rvFolderRecyclerview.visibility = View.VISIBLE
//        }
//    }

    companion object{
        val SELECT_ADD = 500
    }

}