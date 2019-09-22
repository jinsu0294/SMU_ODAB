package com.example.smu_quiz_2.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable
import com.example.smu_quiz_2.*
import com.example.smu_quiz_2.data_class.Folder
import com.example.smu_quiz_2.data_class.FolderList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class FolderAdapter(val context: Context, val folderList: ArrayList<FolderList>): RecyclerView.Adapter<FolderAdapter.Holder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.folder_item, parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return folderList.size
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(folderList[position], context)
    }

    class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val folderName = itemView.findViewById<TextView>(R.id.tvFolderName)
        val odab=itemView.findViewById<Button>(R.id.btnOdab)
        val quiz = itemView.findViewById<Button>(R.id.btnQuiz)
        val delete = itemView.findViewById<ImageButton>(R.id.btnDelete)

        fun bind(folderList: FolderList, context:Context){
            folderName.text = folderList.title

            // 오답 버튼 리스너
            odab.setOnClickListener {
                val intent = Intent(context, OdabFolderActivity::class.java)
                intent.putExtra("Management_id",folderList.id)
                itemView.context.startActivity(intent)
            }

            // 문제 풀기 리스너
            quiz.setOnClickListener {
                val intent = Intent(context, QuizFolderActivity::class.java)
                intent.putExtra("Management_id",folderList.id)
                itemView.context.startActivity(intent)
            }

            // 삭제 버튼 리스너
            delete.setOnClickListener{
                val intent = Intent(context, DeleteChoiceActivity::class.java)
                intent.putExtra("Management_id",folderList.id)
                intent.putExtra("request","DeleteFolder")
                itemView.context.startActivity(intent)
            }
        }
    }

}