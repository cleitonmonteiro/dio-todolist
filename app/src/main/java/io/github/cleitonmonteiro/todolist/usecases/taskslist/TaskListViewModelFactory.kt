package io.github.cleitonmonteiro.todolist.usecases.taskslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.cleitonmonteiro.todolist.database.TaskDao

class TaskListViewModelFactory(
    private val taskDao: TaskDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskListViewModel::class.java)) {
            return TaskListViewModel(taskDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}