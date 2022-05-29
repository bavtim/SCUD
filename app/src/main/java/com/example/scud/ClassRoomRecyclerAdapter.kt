package com.example.scud

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

interface OnItemClickListener{
    fun onItemClicked(classInfo: ClassInfo)
}
class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    val textViewClassRoom: TextView = itemView.findViewById(R.id.textViewClassRoom)
    val textViewClassRoomType: TextView = itemView.findViewById(R.id.textViewClassRoomType)

    fun bind(classInfo: ClassInfo,clickListener: OnItemClickListener)
    {

        textViewClassRoom.text = textViewClassRoom.text.toString() + " " + classInfo.classroomName
        textViewClassRoomType.text = textViewClassRoomType.text.toString() + " " + classInfo.classroomType

        itemView.setOnClickListener {

            clickListener.onItemClicked(classInfo)
        }
    }

}
class ClassRoomRecyclerAdapter(var dataSet:List<ClassInfo>, val itemClickListener: MainActivity):RecyclerView.Adapter<MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item_classroom,parent,false)
        return MyHolder(view)


    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(myHolder: MyHolder, position: Int) {
        val classInfo = dataSet[position]
        myHolder.bind(classInfo,itemClickListener)
    }
}