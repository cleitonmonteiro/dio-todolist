package io.github.cleitonmonteiro.todolist.usecases.taskslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.cleitonmonteiro.todolist.database.TaskDao
import io.github.cleitonmonteiro.todolist.database.model.Task
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val taskDao: TaskDao
) : ViewModel() {
    private val _tasks = taskDao.getAll()

    val tasks: LiveData<List<Task>> = _tasks

    fun deleteTask(task: Task) = viewModelScope.launch {
        taskDao.delete(task)
    }
}
