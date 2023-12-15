package com.example.cloudrayamobile.view.splashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.cloudrayamobile.databinding.ActivitySplashScreenBinding
import com.example.cloudrayamobile.view.registerList.RegisterList

class SplashScreen : AppCompatActivity() {
    private var _binding: ActivitySplashScreenBinding? = null
    private val binding get() = _binding
    private val counter = 1500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, RegisterList::class.java))
                finish()
            }, counter.toLong()
        )
    }
}