package io.github.cleitonmonteiro.todolist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import io.github.cleitonmonteiro.todolist.databinding.ActivityAddTaskBinding
import io.github.cleitonmonteiro.todolist.datasource.TaskDataSource
import io.github.cleitonmonteiro.todolist.extensions.format
import io.github.cleitonmonteiro.todolist.extensions.text
import io.github.cleitonmonteiro.todolist.model.Task
import java.util.*

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding
    private var currentTaskId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getIntExtra(TASK_ID, -1)?.let { it ->
            currentTaskId = it
            TaskDataSource.getById(it)?.let { task ->
                setTaskFields(task)
            }
        }

        insertListeners()
    }

    private fun setTaskFields(task: Task) {
        binding.tilTitle.text = task.title
        binding.tilDescription.text = task.description
        binding.tilDate.text = task.date
        binding.tilTime.text = task.time
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
            if (currentTaskId != null) {
                updateTask(currentTaskId!!)
            } else {
                createNewTask()
            }
            setResult(NEW_TASK_RESULT_CODE)
            finish()
        }
    }

    private fun updateTask(id: Int) {
        val task = Task(
            id = id,
            title = binding.tilTitle.text,
            description = binding.tilDescription.text,
            time = binding.tilTime.text,
            date = binding.tilDate.text,
        )
        TaskDataSource.update(task)
    }

    private fun createNewTask() {
        val task = Task(
            title = binding.tilTitle.text,
            description = binding.tilDescription.text,
            time = binding.tilTime.text,
            date = binding.tilDate.text,
        )
        TaskDataSource.insert(task)
    }

    companion object {
        const val NEW_TASK_RESULT_CODE = 7
        const val TASK_ID = "TASK_ID"
    }
}