package com.example.insertdatafirebasedemo.splashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.insertdatafirebasedemo.databinding.ActivitySplashScreenBinding
import com.example.insertdatafirebasedemo.mainActivity.activity.MainActivity

class SplashScreenActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashScreenBinding
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handler = Handler(Looper.myLooper()!!)
        handler.postDelayed(Runnable {

            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()

        }, 2000)

    }
}