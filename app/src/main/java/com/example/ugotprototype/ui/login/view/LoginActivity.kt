package com.example.ugotprototype.ui.login.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.MainActivity
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityLoginBinding
import com.example.ugotprototype.ui.login.viewmodel.LoginViewModel
import com.example.ugotprototype.ui.sign.view.SignActivity
import com.example.ugotprototype.ui.sign.view.SignNoEmailActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private val kakaoClient: UserApiClient by lazy { UserApiClient.instance }

    companion object {
        const val LOGIN_EMAIL = "loginEmail"
        const val LOGIN_REAL_NAME = "loginRealName"
    }

    // 카카오계정으로 로그인 공통 callback 구성
    // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
    private val kakaOAuthCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        token?.let { getKakaoTalkUserInfo() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        // TODO 필수 : kakao developers -> 플랫폼 -> 키 해시 등록
        Log.e("태그", Utility.getKeyHash(applicationContext))

        binding.imgKakaoLogin.setOnClickListener {
            kakaoLogin()
        }
    }

    private fun kakaoLogin() {
        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (kakaoClient.isKakaoTalkLoginAvailable(this)) {
            handleKakaoTalkLoginResult()
        } else {
            kakaoClient.loginWithKakaoAccount(this, callback = kakaOAuthCallback)
        }
    }

    private fun handleKakaoTalkLoginResult() {
        kakaoClient.loginWithKakaoTalk(this) { token, error ->
            // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
            // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                return@loginWithKakaoTalk
            }
            // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
            error?.let { kakaoClient.loginWithKakaoAccount(this, callback = kakaOAuthCallback) }
            token?.let { getKakaoTalkUserInfo() }
        }
    }

    private fun getKakaoTalkUserInfo() {
        kakaoClient.me { user, _ ->
            user.let {
                if (it?.kakaoAccount?.isEmailValid == true) {
                    // TODO 이메일 정보까지 전달
                    Intent(this, SignActivity::class.java).apply {
                        putExtra(LOGIN_EMAIL, it.kakaoAccount?.email)
                        putExtra(LOGIN_REAL_NAME, it.properties?.get("nickname"))
                        startActivity(this)
                    }
                } else {
                    startActivity(Intent(this, SignNoEmailActivity::class.java))
                }
            }
        }
    }
}