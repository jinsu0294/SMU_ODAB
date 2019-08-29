package com.example.smu_quiz_2.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.example.smu_quiz_2.R
import com.example.smu_quiz_2.Quiz
import com.example.smu_quiz_2.QuizDetail

class QuizFolderAdapter(var context: Context, var quizList: ArrayList<Quiz>):RecyclerView.Adapter<QuizFolderAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.quiz_folder_item,parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return quizList.size
    }

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        holder?.bind(quizList[position],context, position)
    }

    class Holder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(quizList: Quiz, context: Context, position: Int){
            val quizTitle = itemView.findViewById<TextView>(R.id.tvQuizTitle)
            val checkbox=itemView.findViewById<CheckBox>(R.id.checkbox)
            quizTitle.text = quizList.title

            // 아이템 클릭 리스너
            itemView.setOnClickListener {
                val intent = Intent(context,QuizDetail::class.java)
                intent.putExtra("position", position)
                itemView.context.startActivity(intent)
            }
        }
    }
}