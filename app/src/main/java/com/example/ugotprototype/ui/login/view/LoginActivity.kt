package com.example.ugotprototype.ui.login.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.ugotprototype.MainActivity
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityLoginBinding
import com.example.ugotprototype.di.api.ApiService
import com.example.ugotprototype.ui.login.viewmodel.LoginViewModel
import com.example.ugotprototype.ui.team.view.TeamFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var apiService: ApiService

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        loginViewModel.setUserName("az1aee") // 닉네임 더미데이터

        loginViewModel.githubUserName.observe(this) {
            accountExists(it)
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
        val url =
            "https://accounts.kakao.com/weblogin/find_account?lang=ko&continue=%2Flogin%3Fcontinue%3Dhttps%253A%252F%252Faccounts.kakao.com%252Fweblogin%252Faccount%252Finfo"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    fun openMainPwdUrl() {
        val url =
            "https://accounts.kakao.com/weblogin/find_password?lang=ko&continue=%2Flogin%3Fcontinue%3Dhttps%253A%252F%252Faccounts.kakao.com%252Fweblogin%252Faccount%252Finfo"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun accountExists(githubUserName: String) {
        lifecycleScope.launch {
            try {
                val response =
                    apiService.getUser(githubUserName, "Bearer ${TeamFragment.TOKEN_DATA}")
                Log.d("[계정확인]", "해당 계정 존재 : $response")
            } catch (e: Exception) {
                Log.d("[계정확인 불가능]", "해당 계정 미존재")
            }
        }
    }
}