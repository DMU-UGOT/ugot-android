package com.example.ugotprototype.ui.login.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.MainActivity
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityLoginKakaoBinding

class LoginKakaoActivity : AppCompatActivity() {
    private lateinit var binding :ActivityLoginKakaoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_kakao)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_kakao)

        binding.btLoginInput.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.btLoginKakaoFindId.setOnClickListener {
            openEmailUrl()
            finish()
        }

        binding.btLoginKakaoFindPwd.setOnClickListener {
            openPwdUrl()
            finish()
        }
    }

    fun openEmailUrl() {
        val url = "https://accounts.kakao.com/weblogin/find_account?lang=ko&continue=%2Flogin%3Fcontinue%3Dhttps%253A%252F%252Faccounts.kakao.com%252Fweblogin%252Faccount%252Finfo"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    fun openPwdUrl() {
        val url = "https://accounts.kakao.com/weblogin/find_password?lang=ko&continue=%2Flogin%3Fcontinue%3Dhttps%253A%252F%252Faccounts.kakao.com%252Fweblogin%252Faccount%252Finfo"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}