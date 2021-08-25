package io.github.cleitonmonteiro.todolist.database

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TaskDatabaseTest {
    private lateinit var taskDao: TaskDao
    private lateinit var db: TaskDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, TaskDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        taskDao = db.taskDao
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun getTaskDao() {
        assertNotEquals(taskDao, null)
    }
}