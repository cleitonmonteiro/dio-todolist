package io.github.cleitonmonteiro.todolist.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import io.github.cleitonmonteiro.todolist.databinding.ActivityMainBinding
import io.github.cleitonmonteiro.todolist.datasource.TaskDataSource

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTasks.adapter = adapter
        adapter.submitList(TaskDataSource.getAll())
        insertListeners()
    }

    private fun insertListeners() {
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            createActivityLauncher.launch(intent)
        }

        adapter.listenerEdit = {
            Log.e(TAG, "${it.title}")
        }

        adapter.listenerDelete = {
            Log.e(TAG, "${it.title}")
        }
    }

    private val createActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

        when (result.resultCode) {
            AddTaskActivity.NEW_TASK_RESULT_CODE -> {
                binding.rvTasks.adapter = adapter
                adapter.submitList(TaskDataSource.getAll())
            }
        }
    }
}