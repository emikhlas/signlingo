package com.signlingo.main.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.signlingo.R
import com.signlingo.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    private var position = 0
    private lateinit var dataChar: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataChar = resources.getStringArray(R.array.data_char)

        position = intent.getIntExtra(EXTRA_POSITION, 0)
        changePosition()

        binding.apply {
            btnLeft.setOnClickListener(this@DetailActivity)
            btnRight.setOnClickListener(this@DetailActivity)
        }
    }

    fun changePosition() {
        if (position == 0) {
            binding.apply {
                tvLearnLeft.text = dataChar[25]
                tvLearnMid.text = dataChar[position]
                tvLearnRight.text = dataChar[position + 1]
            }
        } else if (position == 25) {
            binding.apply {
                tvLearnLeft.text = dataChar[position - 1]
                tvLearnMid.text = dataChar[position]
                tvLearnRight.text = dataChar[0]
            }
        } else {
            binding.apply {
                tvLearnLeft.text = dataChar[position - 1]
                tvLearnMid.text = dataChar[position]
                tvLearnRight.text = dataChar[position + 1]
            }
        }
        val resUrl = "@drawable/${dataChar[position].lowercase()}"
        val resourceId = resources.getIdentifier(resUrl, null, packageName)
        Log.i("resource :", "$resUrl")
        binding.ivHandsign.setImageResource(resourceId)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_left -> {
                if (position == 0) {
                    position = 25
                } else {
                    position -= 1
                }
                changePosition()
            }
            R.id.btn_right -> {
                if (position == 25) {
                    position = 0
                } else {
                    position += 1
                }
                changePosition()
            }
        }
    }

    companion object {
        const val EXTRA_POSITION = "extra_position"
    }
}