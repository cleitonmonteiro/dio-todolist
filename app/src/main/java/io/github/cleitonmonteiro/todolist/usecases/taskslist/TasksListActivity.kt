package io.github.cleitonmonteiro.todolist.usecases.taskslist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import io.github.cleitonmonteiro.todolist.database.TaskDatabase
import io.github.cleitonmonteiro.todolist.databinding.ActivityMainBinding
import io.github.cleitonmonteiro.todolist.usecases.addedittask.AddEditTaskActivity

class TasksListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TaskListViewModel
    private lateinit var adapter: TaskListAdapter

    companion object {
        private const val TAG = "TasksListActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val taskDao = TaskDatabase.getInstance(this).taskDao
        val viewModelFactory = TaskListViewModelFactory(taskDao)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(TaskListViewModel::class.java)
        adapter = TaskListAdapter()

        binding.rvTasks.adapter = adapter
        insertListeners()
    }

    private fun insertListeners() {
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddEditTaskActivity::class.java)
            createActivityLauncher.launch(intent)
        }

        adapter.listenerEdit = { task ->
            val intent = Intent(this, AddEditTaskActivity::class.java)
            intent.putExtra(AddEditTaskActivity.TASK_EXTRA, task)
            createActivityLauncher.launch(intent)
        }

        adapter.listenerDelete = {
            viewModel.deleteTask(it)
        }

        viewModel.tasks.observe(this, {
            it.let { tasks ->
                binding.includeEmpty.emptyState.visibility =
                    if (tasks.isEmpty()) View.VISIBLE else View.GONE
                adapter.submitList(tasks)
            }
        })
    }

    private val createActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

        when (result.resultCode) {
            AddEditTaskActivity.NEW_TASK_RESULT_CODE -> {
//                updateList()
            }
        }
    }
}