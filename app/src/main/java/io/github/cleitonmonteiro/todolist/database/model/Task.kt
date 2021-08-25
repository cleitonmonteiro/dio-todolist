package io.github.cleitonmonteiro.todolist.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize()
@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "date")
    var date: String,

    @ColumnInfo(name = "time")
    var time: String,

    @ColumnInfo(name = "completed")
    var completed: Boolean = false
) : Parcelable
