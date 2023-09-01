package com.example.ugotprototype.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.MainActivity
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivitySplashBinding
import com.example.ugotprototype.ui.login.view.LoginActivity
import com.example.ugotprototype.SharedPreference

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private var sharedPreference: SharedPreference = SharedPreference(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        goToMain()
    }

    private fun goToMain() {
        if (sharedPreference.getAutoLogin()) {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 1000)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }, 1000)
        }
    }
}