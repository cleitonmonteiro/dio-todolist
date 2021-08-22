package io.github.cleitonmonteiro.todolist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import io.github.cleitonmonteiro.todolist.databinding.ActivityAddTaskBinding
import io.github.cleitonmonteiro.todolist.extensions.format
import io.github.cleitonmonteiro.todolist.extensions.text
import java.util.*

class AddTask : AppCompatActivity() {
    lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        insertListener()
    }

    private fun insertListener() {
        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                binding.tilDate.text = Date(it).format()
            }
            datePicker.show(supportFragmentManager, "NEW_TASK_DATE_PICKER")
        }
    }
}