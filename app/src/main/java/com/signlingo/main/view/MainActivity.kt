package com.signlingo.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.signlingo.R
import com.signlingo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listChar = resources.getStringArray(R.array.data_char)
        binding.rvChar.layoutManager = GridLayoutManager(this@MainActivity,5)
        binding.rvChar.adapter = CharAdapter(listChar)
    }
}