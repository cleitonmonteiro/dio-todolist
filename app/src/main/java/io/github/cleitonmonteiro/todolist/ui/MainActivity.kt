package io.github.cleitonmonteiro.todolist.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
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
        updateList()
        insertListeners()
    }

    private fun updateList() {
        val list = TaskDataSource.getAllSortedById()
        binding.includeEmpty.emptyState.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        adapter.submitList(list)
    }

    private fun insertListeners() {
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            createActivityLauncher.launch(intent)
        }

        adapter.listenerEdit = { task ->
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, task.id)
            createActivityLauncher.launch(intent)
        }

        adapter.listenerDelete = {
            TaskDataSource.delete(it)
            updateList()
        }
    }

    private val createActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

        when (result.resultCode) {
            AddTaskActivity.NEW_TASK_RESULT_CODE -> {
                updateList()
            }
        }
    }
}