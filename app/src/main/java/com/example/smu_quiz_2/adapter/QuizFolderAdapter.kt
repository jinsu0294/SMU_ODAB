package com.example.smu_quiz_2.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import com.example.smu_quiz_2.DeleteChoiceActivity
import com.example.smu_quiz_2.QuizDetail
import com.example.smu_quiz_2.R
import com.example.smu_quiz_2.data_class.QuizList

// 문제풀기 폴더 Adapter
class QuizFolderAdapter(var context: Context, var quizList: ArrayList<QuizList>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<QuizFolderAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.quiz_folder_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return quizList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(quizList[position], context, position)
    }

    class Holder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        val quizTitle = itemView.findViewById<TextView>(R.id.tvQuizTitle)
        val checkbox = itemView.findViewById<CheckBox>(R.id.checkbox)
        val delete = itemView.findViewById<ImageButton>(R.id.btnDeleteQuiz)

        fun bind(quizList: QuizList, context: Context, position: Int) {

            // quiz_folder_item 에서 아이디가 tvQuizTitle인 TextView (= quizTitle)의 text를 넘어온 quizList의 title로 설정
            quizTitle.text = quizList.title
            // quiz_folder_item 에서 아이디가 checkbox인 CheckBox (= checkbos)의 isChecked 속성을 false로 설정
            checkbox.isChecked = quizList.isChecked

            checkbox.setOnClickListener{
                quizList.isChecked = checkbox.isChecked
            }
            // 아이템 클릭 리스너
            itemView.setOnClickListener {
                val intent = Intent(context, QuizDetail::class.java)
                intent.putExtra("quiz_id",quizList.quiz_id)
                intent.putExtra("position", position)
                itemView.context.startActivity(intent)
            }

            // 삭제 버튼 리스너
            delete.setOnClickListener{
                val intent = Intent(context, DeleteChoiceActivity::class.java)
                intent.putExtra("quiz_id", quizList.quiz_id)
                intent.putExtra("request","DeleteQuiz")
                itemView.context.startActivity(intent)
            }


        }
    }

}