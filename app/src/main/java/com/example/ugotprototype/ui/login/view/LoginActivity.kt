package com.example.ugotprototype.ui.login.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.ugotprototype.MainActivity
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityLoginBinding
import com.example.ugotprototype.di.api.ApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    @Inject
    lateinit var apiClient: ApiClient

    val authToken = "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"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        lifecycleScope.launch {
            apiClient.fetchUserData(authToken)
        }

        binding.btLoginKakao.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.btLoginMainInput.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.btLoginMainFindId.setOnClickListener {
            openMainEmailUrl()
            finish()
        }

        binding.btLoginMainFindPwd.setOnClickListener {
            openMainPwdUrl()
            finish()
        }
    }

    fun openMainEmailUrl() {
        val url = "https://accounts.kakao.com/weblogin/find_account?lang=ko&continue=%2Flogin%3Fcontinue%3Dhttps%253A%252F%252Faccounts.kakao.com%252Fweblogin%252Faccount%252Finfo"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    fun openMainPwdUrl() {
        val url = "https://accounts.kakao.com/weblogin/find_password?lang=ko&continue=%2Flogin%3Fcontinue%3Dhttps%253A%252F%252Faccounts.kakao.com%252Fweblogin%252Faccount%252Finfo"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}