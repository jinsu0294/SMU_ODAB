package com.example.smu_quiz_2.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.smu_quiz_2.Odab
import com.example.smu_quiz_2.OdabDetail
import com.example.smu_quiz_2.R
import org.w3c.dom.Text


class OdabFolderAdapter(val context: Context, val odablist: ArrayList<Odab>): RecyclerView.Adapter<OdabFolderAdapter.Holder>(){


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.odab_folder_item, parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return odablist.size
    }

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        holder?.bind(odablist[position], context, position)
    }

    class Holder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bind(odablist: Odab, context:Context, position: Int){
            val title = itemView.findViewById<TextView>(R.id.tvOdabTitle)
            title.text = odablist.title

            // item 클릭 리스너
            itemView.setOnClickListener {
                val intent = Intent(context,OdabDetail::class.java)
                intent.putExtra("position", position)
                itemView.context.startActivity(intent)
            }
        }
    }

}