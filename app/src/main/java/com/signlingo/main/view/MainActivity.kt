package com.signlingo.main.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.signlingo.R
import com.signlingo.databinding.ActivityMainBinding
import com.signlingo.quiz.view.QuizActivity

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listChar = resources.getStringArray(R.array.data_char)
        binding.rvChar.layoutManager = GridLayoutManager(this@MainActivity, 5)
        binding.rvChar.adapter = CharAdapter(listChar)

        binding.btnQuickquiz.setOnClickListener {
            val intent = Intent(this@MainActivity, QuizActivity::class.java)
            startActivity(intent)
        }
    }
}