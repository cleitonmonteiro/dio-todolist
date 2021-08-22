package io.github.cleitonmonteiro.todolist.ui

import android.os.Bundle
import android.util.Log
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

class AddTask : AppCompatActivity() {
    lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                binding.tilTime.text = String.format("%02d:%02d", timerPicker.hour, timerPicker.minute)
            }
            timerPicker.show(supportFragmentManager, "NEW_TASK_TIMER_PICKER")
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.btnNewTask.setOnClickListener {
            val task = Task(
                title = binding.tilTitle.text,
                description = binding.tilDescription.text,
                time = binding.tilTime.text,
                date = binding.tilDate.text,
            )
            TaskDataSource.insert(task)
        }
    }
}