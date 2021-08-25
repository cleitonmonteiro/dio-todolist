package io.github.cleitonmonteiro.todolist.usecases.addedittask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.cleitonmonteiro.todolist.database.TaskDao

class AddEditTaskViewModelFactory(
    private val taskDao: TaskDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddEditTaskViewModel::class.java)) {
            return AddEditTaskViewModel(taskDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}