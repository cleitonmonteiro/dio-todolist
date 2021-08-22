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

    fun getAll() = list.toList()

    fun getAllSortedById() = list.sortedBy { it.id }

    fun insert(task: Task) {
        list.add(task.copy(id = list.size + 1))
    }

    fun delete(task: Task) {
        list.remove(task)
    }

    fun update(task: Task) {
        val oldTask = getById(task.id)
        list.remove(oldTask)
        list.add(task)
    }

    fun getById(id: Int): Task? {
        return list.find { it.id == id }
    }
}