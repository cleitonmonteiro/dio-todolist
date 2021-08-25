package io.github.cleitonmonteiro.todolist.usecases.taskslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.cleitonmonteiro.todolist.R
import io.github.cleitonmonteiro.todolist.database.TaskDao
import io.github.cleitonmonteiro.todolist.database.model.Task
import io.github.cleitonmonteiro.todolist.databinding.TaskItemBinding

class TaskListAdapter:
    ListAdapter<Task, TaskListAdapter.TaskViewHolder>(TaskDiffCallback()) {
    var listenerEdit: (Task) -> Unit = {}
    var listenerDelete: (Task) -> Unit = {}
    var listenerComplete: (Task, Boolean) -> Unit = {task, isChecked -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TaskItemBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(private val binding: TaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Task) {
            binding.tvTitle.text = item.title
            binding.tvDescription.text = item.description
            binding.tvDateTime.text = "${item.date} ${item.time}"
            binding.ivMore.setOnClickListener {
                showMenuPopup(it, item)
            }
            binding.cbComplete.isChecked = item.completed
            binding.cbComplete.setOnClickListener {
                listenerComplete(item, (it as CompoundButton).isChecked)
            }
        }

        private fun showMenuPopup(ivMore: View, task: Task) {
            val popupMenu = PopupMenu(ivMore.context, ivMore)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_edit -> listenerEdit(task)
                    R.id.action_delete -> listenerDelete(task)
                }

                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }

    }
}

class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem == newItem
}