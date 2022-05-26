package com.signlingo.auth.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.signlingo.main.view.CameraActivity
import com.signlingo.main.view.MainActivity
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showHome()
    }

    private fun showHome() {
        Executors.newSingleThreadScheduledExecutor().schedule( {
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()
        }, 2, TimeUnit.SECONDS)
    }
}