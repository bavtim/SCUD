package com.example.scud

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ClassRoomRecyclerAdapter(private val dataSet: List<ClassInfo>) :
    RecyclerView.Adapter<ClassRoomRecyclerAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewClassRoom: TextView = itemView.findViewById(R.id.textViewClassRoom)
        val textViewClassRoomType: TextView = itemView.findViewById(R.id.textViewClassRoomType)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_item_classroom, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Log.d("RETROFIT: ", dataSet[position].classroomName?: "N/A")
        Log.d("RETROFIT: ", dataSet[position].classroomType?: "N/A")
        viewHolder.textViewClassRoom.text = viewHolder.textViewClassRoom.text.toString() + " " + dataSet[position].classroomName
        viewHolder.textViewClassRoomType.text = viewHolder.textViewClassRoomType.text.toString() + " " + dataSet[position].classroomType
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}