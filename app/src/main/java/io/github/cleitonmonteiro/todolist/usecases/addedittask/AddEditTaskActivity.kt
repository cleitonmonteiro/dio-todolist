package io.github.cleitonmonteiro.todolist.usecases.addedittask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import io.github.cleitonmonteiro.todolist.database.TaskDatabase
import io.github.cleitonmonteiro.todolist.database.model.Task
import io.github.cleitonmonteiro.todolist.databinding.ActivityAddTaskBinding
import io.github.cleitonmonteiro.todolist.extensions.format
import io.github.cleitonmonteiro.todolist.extensions.text
import java.util.*

class AddEditTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var viewModel: AddEditTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val taskDao = TaskDatabase.getInstance(this).taskDao
        val viewModelFactory = AddEditTaskViewModelFactory(taskDao)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(AddEditTaskViewModel::class.java)

        intent.getParcelableExtra<Task>(TASK_EXTRA)?.let { it ->
            viewModel.updateCurrentTask(it)
        }

        insertListeners()
    }

    private fun insertListeners() {
        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                binding.tilDate.text = Date(it).format()
            }
            datePicker.show(supportFragmentManager, "NEW_TASK_DATE_PICKER")
        }

        binding.tilTime.editText?.setOnClickListener {
            val timerPicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()

            timerPicker.addOnPositiveButtonClickListener {
                binding.tilTime.text =
                    String.format("%02d:%02d", timerPicker.hour, timerPicker.minute)
            }
            timerPicker.show(supportFragmentManager, "NEW_TASK_TIMER_PICKER")
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.btnSaveTask.setOnClickListener {
            val task = Task(
                title = binding.tilTitle.text,
                description = binding.tilDescription.text,
                time = binding.tilTime.text,
                date = binding.tilDate.text,
            )
            viewModel.saveTask(task)
            setResult(NEW_TASK_RESULT_CODE)
            finish()
        }

        viewModel.currentTask.observe(this, { currentTask ->
            if (currentTask != null) {
                setTaskFields(task = currentTask)
            }
        })
    }

    private fun setTaskFields(task: Task) {
        binding.tilTitle.text = task.title
        binding.tilDescription.text = task.description
        binding.tilDate.text = task.date
        binding.tilTime.text = task.time
    }

    companion object {
        const val NEW_TASK_RESULT_CODE = 7
        const val TASK_EXTRA = "TASK_EXTRA"
    }
}