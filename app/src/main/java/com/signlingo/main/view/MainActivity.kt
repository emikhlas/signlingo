package com.signlingo.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.signlingo.R
import com.signlingo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvSeeall.setOnClickListener{
            if (binding.row3.visibility == View.GONE){
                binding.row3.visibility = View.VISIBLE
                binding.row4.visibility = View.VISIBLE
                binding.row5.visibility = View.VISIBLE
                binding.row6.visibility = View.VISIBLE
                binding.tvSeeall.text = "See Less"
            } else {
                binding.row3.visibility = View.GONE
                binding.row4.visibility = View.GONE
                binding.row5.visibility = View.GONE
                binding.row6.visibility = View.GONE
                binding.tvSeeall.text = "See All"
            }

        }
    }
}