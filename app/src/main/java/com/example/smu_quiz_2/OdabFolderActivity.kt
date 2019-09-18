package com.example.smu_quiz_2

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smu_quiz_2.adapter.OdabFolderAdapter
import com.example.smu_quiz_2.data_class.WrongList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_quiz_folder.*
import kotlinx.android.synthetic.main.activity_userfolder.*
import kotlinx.android.synthetic.main.activity_userfolder.btnAdd
import kotlinx.android.synthetic.main.activity_userfolder.tvNothing

class OdabFolderActivity : AppCompatActivity() {

    var smuOdabAPI = SmuOdabAPI()
    var smuInfoRetrofit = smuOdabAPI.smuInfoRetrofit()
    var smuOdabInterface = smuInfoRetrofit.create(SmuOdabInterface::class.java)
    var wronglist = arrayListOf<WrongList>()

    // 받아온 데이터 저장할 리스트
    var wrongList = arrayListOf<WrongList>()

    fun checkFolder() {
        // view 세팅
        tvId.visibility = View.GONE
        btnLogout.visibility = View.GONE

        val user = application as User
        val mAdapter = OdabFolderAdapter(this, wronglist)
        Log.e("오답리스트 사이즈", mAdapter.itemCount.toString())

        // 폴더가 없는 경우
        if (mAdapter.itemCount == 0) {
            tvNothing.visibility = View.VISIBLE
            rvFolderRecyclerview.visibility = View.INVISIBLE

        } else {  // 폴더가 있는 경우
            tvNothing.visibility = View.GONE
            rvFolderRecyclerview.visibility = View.VISIBLE
        }
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userfolder)

        val Management_id = intent.getIntExtra("Management_id", 1)
        wronglist.add(WrongList(1,"22",3))
        var mAdapter = OdabFolderAdapter(this, wronglist)
        rvFolderRecyclerview.adapter = mAdapter

        // TODO:: 오답노트 리스트조회 getWrongList()
        // Management_id(폴더 아이디)를 넘겨서 오답노트 리스트를 받아옵니다.
        // for(i in 0..list.size-1)
        //      wrongList.add(WrongList(list[i].wrong_id,list[i].title, Management_id(이건 받아와야하는것))

        smuOdabInterface.getWrongList(Management_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                wronglist.clear()
                for (i in 0..list.size)
                    wronglist[i] = list[i]
                mAdapter = OdabFolderAdapter(this, wronglist)
                rvQuizRecyclerView.adapter = mAdapter
                rvQuizRecyclerView.layoutManager = LinearLayoutManager(this)
                rvFolderRecyclerview.setHasFixedSize(true)
                Log.e("123123", "getWrongList" + list.size.toString())
            }, { error ->
                Log.e("123123", "getWrongList")
                error.printStackTrace()
            }, {
                Log.e("123123", "getWrongList")
            })

        checkFolder()

        // 리사이클러뷰 adapter 설정

        // 추가 버튼 리스너
        btnAdd.setOnClickListener {
            val intent = Intent(this, OdabAddActivity::class.java)
            startActivityForResult(intent, CHANGE_ACTIVITY)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            finish()
        }

    }

    companion object {
        val CHANGE_ACTIVITY = 100
    }
}