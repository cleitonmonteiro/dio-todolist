package io.github.cleitonmonteiro.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.cleitonmonteiro.todolist.database.model.Task

@Dao
interface TaskDao {
    @Insert
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Update
    suspend fun update(task: Task)

    @Query("SELECT * FROM task_table ORDER BY id DESC")
    fun getAll(): LiveData<List<Task>>

    @Query("SELECT * from task_table WHERE id = :id")
    suspend fun getById(id :Int): Task?

    @Query("DELETE from task_table")
    suspend fun deleteAll()
}