package com.signlingo.quiz.view

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.signlingo.BuildConfig
import com.signlingo.R
import com.signlingo.databinding.ActivityQuizBinding
import com.signlingo.main.view.FalseActivity
import com.signlingo.main.view.TrueActivity
import com.signlingo.quiz.data.ApiConfig
import com.signlingo.quiz.data.QuizResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuizActivity : AppCompatActivity() {
    private var _binding: ActivityQuizBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getQuiz()

        binding.btnSubmit.setOnClickListener {
            if(binding.edtQuiz.text.toString().uppercase() == binding.imgQuiz.contentDescription.toString()) {
                val intent = Intent(this@QuizActivity, TrueActivity::class.java)
                intent.putExtra(TrueActivity.EXTRA_POSITION, 100)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this@QuizActivity, FalseActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun getQuiz() {
        val service = ApiConfig.getApiService().getQuiz(BuildConfig.APP_KEY)
        service.enqueue(object : Callback<QuizResponse> {
            override fun onResponse(
                call: Call<QuizResponse>,
                response: Response<QuizResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setQuizData(responseBody.imageurl, responseBody.label)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<QuizResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setQuizData(imageurl: String, label: String) {
        Glide.with(this@QuizActivity)
            .load(imageurl)
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_error)
            .transform(RoundedCorners(50))
            .into(binding.imgQuiz)
        binding.imgQuiz.contentDescription = label
    }
}