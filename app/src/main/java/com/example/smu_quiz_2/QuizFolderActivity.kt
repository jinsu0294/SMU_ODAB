package com.example.smu_quiz_2

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smu_quiz_2.adapter.QuizFolderAdapter
import com.example.smu_quiz_2.data_class.Quiz
import com.example.smu_quiz_2.data_class.QuizList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_quiz_folder.*

class QuizFolderActivity : AppCompatActivity() {

    var smuOdabAPI = SmuOdabAPI()
    var smuInfoRetrofit = smuOdabAPI.smuInfoRetrofit()
    var smuOdabInterface = smuInfoRetrofit.create(SmuOdabInterface::class.java)
    var quizlist = arrayListOf<QuizList>()

    val quizId: ArrayList<Int> = arrayListOf<Int>()
    var selectquiz = arrayListOf<Quiz>()

    // 뷰 처리하는 코드 빼고 없애도 될 듯(통신 되면)
    // 폴더가 있는지 없는지 확인하는 함수
    fun checkFolder(mAdapter: QuizFolderAdapter) {
        Log.e("USER QUIZLIST", quizlist.size.toString())   // 저장되어있는 quizlist 확인하려는 Log
        // 폴더가 없는 경우
        if (mAdapter.itemCount == 0) {
            tvNothing.visibility = View.VISIBLE // 입력값이 없습니다 표시하는 textView 보이게
            rvQuizRecyclerView.visibility = View.GONE   // 리사이클러뷰 안 보이게
            btnAllCheck.visibility = View.GONE  // 전체선택 버튼 없앰(GONE 으로 설정 했음)
        } else {  // 폴더가 있는 경우
            tvNothing.visibility = View.GONE    // 입력값이 없습니다 표시하는 textView 없앰(GONE)
            rvQuizRecyclerView.visibility = View.VISIBLE    // 리라이클러뷰 보이게
            btnAllCheck.visibility = View.VISIBLE   // 전체선택 버튼 보이게
        }
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_folder)
        val Management_id = intent.getIntExtra(
            "Management_id", 0
        )
        Log.e("123123", "Management_id_QuizFolderActivity_" + Management_id)

        quizlist.add(QuizList(1, "title", 2, true))
        var mAdapter = QuizFolderAdapter(this, quizlist)

        // TODO:: OK 퀴즈 리스트조회 getQuizList
        // Management_id (폴더 아이디)를 보내서 퀴즈 폴더 리스트를 받아옵니다.
        // quiz_id, title, Management_id 가 옵니다.
        // title 만 리스트에 보여주면 됩니다.
        smuOdabInterface.getQuizList(Management_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                Log.e("folderListSize", list.size.toString())
                quizlist.clear()
                for (i in 0 until list.size)
                    quizlist.add(list[i])
                mAdapter = QuizFolderAdapter(this, quizlist)
                rvQuizRecyclerView.adapter = mAdapter
                // 레아아웃 매니저 설정
                rvQuizRecyclerView.layoutManager = LinearLayoutManager(this)
                rvQuizRecyclerView.setHasFixedSize(true)
            }, { error ->
                error.printStackTrace()
            }, {
            })

        // 폴더 있는지 없는지 확인 함수 호출
        checkFolder(mAdapter)

        // 추가 버튼 리스너
        btnAdd.setOnClickListener {
            // QuizAdd 액티비티로 이동
            val intent = Intent(this, QuizAdd::class.java)
            intent.putExtra("Management_id", Management_id)
            Log.e("123123", "Management_id" + Management_id)
            startActivityForResult(intent, 300)
        }

        var click = -1
        // 전체선택 버튼 리스너
        btnAllCheck.setOnClickListener {
            // 홀수 번 클릭 했을 때
            if (click == -1) {
                click = 0
                for (i in 0..quizlist.size - 1) {
                    mAdapter.quizList[i].isChecked = true   // 체크박스 선택(O)
                }
            } else {  // 짝수 번 클릭했을 때
                click = -1
                for (i in 0..quizlist.size - 1) {
                    mAdapter.quizList[i].isChecked = false  // 체크박스 선택(X)
                }
            }
            // 바뀐 quizlist를 adpater로 넘기고 다시 adapter설정함 -> 이렇게 하면 checkBox 전체가 선택되고 해제됨
            val mAdapter = QuizFolderAdapter(this, quizlist)
            rvQuizRecyclerView.adapter = mAdapter
        }
        val user = application as User
        var j=0
        btnSolve.setOnClickListener {
            quizId.clear()
            for (i in 0..quizlist.size - 1) {
                if (mAdapter.quizList[i].isChecked) {
                    quizId.add(mAdapter.quizList[i].quiz_id!!)
                }
            }
            val intent = Intent(this, QuizSolveActivity::class.java)
            intent.putExtra("quizId",quizId)
            startActivity(intent)
        }
    }

    // resultCode -> RESULT_OK 이면, 액티비티 종료
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> finish()
        }
    }
}