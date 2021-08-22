package io.github.cleitonmonteiro.todolist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.cleitonmonteiro.todolist.R
import io.github.cleitonmonteiro.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}