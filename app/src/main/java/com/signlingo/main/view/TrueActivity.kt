package com.signlingo.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.signlingo.databinding.ActivityTrueBinding

class TrueActivity : AppCompatActivity() {
    private var _binding: ActivityTrueBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTrueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val position = intent.getIntExtra(EXTRA_POSITION, 0)

        binding.btnContinue.setOnClickListener {
            if(position == 100) {
                finish()
            } else {
                val intent = Intent(this@TrueActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_POSITION, position + 1)
                startActivity(intent)
                finish()
            }
        }
    }

    companion object {
        const val EXTRA_POSITION = "extra_position"
    }
}