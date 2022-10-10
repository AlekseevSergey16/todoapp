package com.salekseev.notification.mytodoapp2.ui.tasklist

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.salekseev.notification.mytodoapp2.R
import com.salekseev.notification.mytodoapp2.model.Task

interface OnItemClickListener {
    fun onItemClicked(task: Task)
    fun onCheckBoxClicked(taskId: Long, checked: Boolean)
}

class TaskListAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<Task, TaskListAdapter.TaskHolder>(ItemCallback) {

//    override fun onClick(v: View) {
//        val task = v.tag as Task
//        when (v.id) {
//            R.id.completed_checkbox -> listener.onCheckBoxClicked(task.id!!)
//            else -> listener.onItemClicked(task)
//        }
//    }

    inner class TaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView = itemView.findViewById<TextView>(R.id.title_task_textview)
        val completedCheckBox = itemView.findViewById<CheckBox>(R.id.completed_checkbox)

        fun bind(task: Task) {
            titleTextView.text = task.title
            completedCheckBox.isChecked = task.completed

            itemView.setOnClickListener {
                listener.onItemClicked(task)
            }

            completedCheckBox.setOnClickListener {
                listener.onCheckBoxClicked(task.id!!, !task.completed)
            }

            if (task.completed) {
                titleTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                titleTextView.paintFlags = Paint.ANTI_ALIAS_FLAG
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_item, parent, false)

        return TaskHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)
    }

    object ItemCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }

}