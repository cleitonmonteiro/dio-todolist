package io.github.cleitonmonteiro.todolist.datasource

import io.github.cleitonmonteiro.todolist.model.Task

object TaskDataSource {
    private val list = mutableListOf<Task>()

    fun getAll() =  list

    fun insert(task: Task) {
        list.add(task.copy(id = list.size + 1))
    }
}