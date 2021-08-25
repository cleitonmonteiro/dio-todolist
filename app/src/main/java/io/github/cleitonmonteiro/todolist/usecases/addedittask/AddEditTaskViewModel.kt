package io.github.cleitonmonteiro.todolist.usecases.addedittask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.cleitonmonteiro.todolist.database.TaskDao
import io.github.cleitonmonteiro.todolist.database.model.Task
import kotlinx.coroutines.launch

class AddEditTaskViewModel(
    private val taskDao: TaskDao
) : ViewModel() {

    private val _currentTask = MutableLiveData<Task>()

    val currentTask: LiveData<Task> = _currentTask

    fun updateCurrentTask(task: Task?) {
        _currentTask.value = task
    }

    fun saveTask(task: Task) = viewModelScope.launch {
        if (_currentTask.value != null) {
            updateTask(task.copy(id = currentTask.value!!.id))
        } else {
            insertTask(task)
        }
    }

    private fun insertTask(task: Task) = viewModelScope.launch {
        taskDao.insert(task)
    }

    private fun updateTask(task: Task) = viewModelScope.launch {
        taskDao.update(task)
    }
}
