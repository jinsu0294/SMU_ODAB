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
    var wrongList = ArrayList<WrongList>()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userfolder)

<<<<<<< Updated upstream
        val Management_id = intent.getIntExtra("Management_id", 1)
        wronglist.add(WrongList(1,"22",3))
        var mAdapter = OdabFolderAdapter(this, wronglist)
        rvFolderRecyclerview.adapter = mAdapter

        // TODO:: 오답노트 리스트조회 getWrongList()
        // Management_id(폴더 아이디)를 넘겨서 오답노트 리스트를 받아옵니다.
        // for(index in 0..list.size-1)
        //      wrongList.add(WrongList(list[index].wrong_id,list[index].title, Management_id(이건 받아와야하는것))
=======
        val Management_id = intent.getIntExtra("Management_id", -1)
        Log.e("folderId", Management_id.toString())
>>>>>>> Stashed changes

        // TODO:: OK 오답노트 리스트조회 getWrongList()
        smuOdabInterface.getWrongList(Management_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                Log.e("wrongListSize", list.size.toString())
//                wrongList.clear()
                for (i in 0 until list.size)
                    wrongList.add(WrongList(list[i].wrong_id, list[i].title, Management_id))

                tvNothing.visibility = if(list.isEmpty()) View.VISIBLE else View.GONE
                rvFolderRecyclerview.visibility = if(list.isEmpty()) View.INVISIBLE else View.VISIBLE

                // adapter 설정
                val mAdapter = OdabFolderAdapter(this, wrongList)
                rvFolderRecyclerview.adapter = mAdapter
                // 레이아웃 매니저 설정
                rvFolderRecyclerview.layoutManager = LinearLayoutManager(this)
                rvFolderRecyclerview.setHasFixedSize(true)

            }, { error ->
                Log.e("123123", "getWrongList")
                error.printStackTrace()
            }, {
                Log.e("123123", "complette")
            })

//        checkFolder(mAdapter)


        // view 세팅
        tvId.visibility = View.GONE
        btnLogout.visibility = View.GONE

        // 추가 버튼 리스너
        btnAdd.setOnClickListener {
            val intent = Intent(this, OdabAddActivity::class.java)
            intent.putExtra("Management_id", Management_id)
            startActivityForResult(intent, CHANGE_ACTIVITY)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(resultCode){
            Activity.RESULT_OK ->{
                finish()
                Log.e("OdabFolderAdd","!!yes!!")
            }
        }

    }

    companion object {
        val CHANGE_ACTIVITY = 100
    }
}