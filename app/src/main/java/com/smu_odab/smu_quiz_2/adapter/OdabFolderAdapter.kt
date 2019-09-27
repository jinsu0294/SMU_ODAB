package com.smu_odab.smu_quiz_2.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.smu_odab.smu_quiz_2.DeleteChoiceActivity
import com.smu_odab.smu_quiz_2.OdabDetailActivity
import com.smu_odab.smu_quiz_2.R
import com.smu_odab.smu_quiz_2.data_class.WrongList


class OdabFolderAdapter(val context: Context, val odablist: ArrayList<WrongList>): RecyclerView.Adapter<OdabFolderAdapter.Holder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.odab_folder_item, parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return odablist.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(odablist[position], context, position)
    }

    class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.tvOdabTitle)
        val delete = itemView.findViewById<ImageButton>(R.id.btnDelete)

        fun bind(odablist: WrongList, context:Context, position: Int){

            title.text = odablist.title

            // item 클릭 리스너
            itemView.setOnClickListener {
                val intent = Intent(context,OdabDetailActivity::class.java)
                intent.putExtra("position", position)
                intent.putExtra("wrong_id", odablist.wrong_id)
                Log.e("Odab_Folder_Id", odablist.wrong_id.toString())
                itemView.context.startActivity(intent)
            }

            // 삭제 버튼 리스너
            delete.setOnClickListener {
                val intent = Intent(context, DeleteChoiceActivity::class.java)
                intent.putExtra("wrong_id", odablist.wrong_id)
                intent.putExtra("request", "DeleteOdab")
                itemView.context.startActivity(intent)
            }
        }
    }

}