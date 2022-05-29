package com.signlingo.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.signlingo.databinding.ActivityFalseBinding

class FalseActivity : AppCompatActivity() {
    private var _binding: ActivityFalseBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFalseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener {
            finish()
        }
    }
}