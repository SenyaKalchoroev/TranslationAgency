package com.kwork.translationagency.presentation.ui.fragments.tasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kwork.translationagency.databinding.ItemTaskBinding
import com.kwork.translationagency.domain.model.TaskModel

class TaskAdapter(
    private val tasks: List<TaskModel>
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: TaskModel) {
            binding.itemImgTask.setImageResource(task.imageRes)
            binding.itemTaskName.text = task.taskName
            binding.itemSecondDate.text = task.secondDate
            binding.itemFirstDate.text = task.firstDate
            binding.itemTaskTime.text = task.taskTime
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size
}
