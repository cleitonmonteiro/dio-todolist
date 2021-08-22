package io.github.cleitonmonteiro.todolist.datasource

import io.github.cleitonmonteiro.todolist.model.Task

object TaskDataSource {
    private val list = mutableListOf<Task>(
        Task(
            id = 0,
            time = "12:21",
            title = "Do this app",
            description = "Use kotlin and android to this",
            date = "12/12/2021"
        )
    )

    fun getAll() =  list

    fun insert(task: Task) {
        list.add(task.copy(id = list.size + 1))
    }
}