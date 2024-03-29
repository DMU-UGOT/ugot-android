package com.example.ugotprototype.ui.login.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.BuildConfig
import com.example.ugotprototype.MainActivity
import com.example.ugotprototype.R
import com.example.ugotprototype.SharedPreference
import com.example.ugotprototype.data.oauth.RequestLoginNaver
import com.example.ugotprototype.databinding.ActivityLoginBinding
import com.example.ugotprototype.ui.login.viewmodel.LoginViewModel
import com.example.ugotprototype.ui.sign.view.SignActivity
import com.example.ugotprototype.ui.sign.view.SignNoEmailActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreference: SharedPreference
    private val viewModel: LoginViewModel by viewModels()
    private val kakaoClient: UserApiClient by lazy { UserApiClient.instance }
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var signInLauncher: ActivityResultLauncher<Intent>
    private var email: String = ""
    private var displayName: String = ""

    companion object {
        const val LOGIN_EMAIL = "loginEmail"
        const val LOGIN_REAL_NAME = "loginRealName"
        var USER_LOGIN_TYPE = ""
    }

    // 카카오계정으로 로그인 공통 callback 구성
    // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
    private val kakaOAuthCallback: (OAuthToken?, Throwable?) -> Unit = { token, _ ->
        token?.let { getKakaoTalkUserInfo(token.accessToken) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        sharedPreference = SharedPreference(this)

        // TODO 필수 : kakao developers -> 플랫폼 -> 키 해시 등록
        Log.e("태그", Utility.getKeyHash(applicationContext))

        viewModel.userAccessToken.observe(this) {
            if (it != "") {
                // TODO accessToken 저장
                sharedPreference.saveToken(it!!)
                sharedPreference.saveAutoLogin(true)
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                // 계졍이 없는 경우 회원가입 진행
                getNaverProfile()
            }
        }

        viewModel.kakaoUserAccessToken.observe(this) {
            if (it != "") {
                sharedPreference.saveToken(it!!)
                sharedPreference.saveAutoLogin(true)
                startActivity(Intent(this, MainActivity::class.java))
            }
            Log.d("test", "로그인 실패")
        }

        viewModel.buserAccessToken.observe(this) {
            if (it != null) {
                sharedPreference.saveToken(it.toString())
                sharedPreference.saveAutoLogin(true)
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                registerGoogleAccount(email, displayName)
            }
        }

        viewModel.auserAccessToken.observe(this) {
            viewModel.loginGoogle(it.toString())
        }

        binding.layoutNaverLogin.setOnClickListener {
            loginNaver()
        }

        binding.imgKakaoLogin.setOnClickListener {
            kakaoLogin()
        }

        setUpGoogleLogin()
    }

    private fun setUpKaKaoLogin() {
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_KEY)
    }

    private fun kakaoLogin() {
        setUpKaKaoLogin()

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (kakaoClient.isKakaoTalkLoginAvailable(this)) {
            handleKakaoTalkLoginResult()
        } else {
            kakaoClient.loginWithKakaoAccount(this, callback = kakaOAuthCallback)
        }
    }

    private fun handleKakaoTalkLoginResult() {
        kakaoClient.loginWithKakaoTalk(this) { token, error ->
            Log.d("token", token?.accessToken.toString())
            // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
            // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                return@loginWithKakaoTalk
            }
            // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
            error?.let { kakaoClient.loginWithKakaoAccount(this, callback = kakaOAuthCallback) }
            token?.let { getKakaoTalkUserInfo(token.accessToken) }
        }
    }

    private fun getKakaoTalkUserInfo(accessToken: String) {
        Log.d("test", accessToken)
        viewModel.loginKakao(accessToken) {
            if (it) {
                kakaoClient.me { user, _ ->
                    user.let { userData ->
                        if (userData?.kakaoAccount?.isEmailValid == true) {
                            Intent(this, SignActivity::class.java).apply {
                                putExtra(LOGIN_EMAIL, userData.kakaoAccount?.email)
                                putExtra(LOGIN_REAL_NAME, userData.properties?.get("nickname"))
                                USER_LOGIN_TYPE = "카카오"
                                startActivity(this)
                            }
                        } else {
                            // 이메일 데이터 필요
                            USER_LOGIN_TYPE = "카카오"
                            startActivity(Intent(this, SignNoEmailActivity::class.java))

                        }
                    }
                }
            }
        }
    }


    private fun setUpNaverLogin() {
        NaverIdLoginSDK.initialize(
            context = this,
            clientId = BuildConfig.NAVER_CLIENT_ID,
            clientSecret = BuildConfig.NAVER_CLIENT_SECRET,
            clientName = BuildConfig.NAVER_CLIENT_ID
        )
    }

    private fun loginNaver() {
        setUpNaverLogin()

        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
                Log.e("태그", NaverIdLoginSDK.getAccessToken().toString())
                viewModel.loginNaver(RequestLoginNaver(NaverIdLoginSDK.getAccessToken().toString()))
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            }

            override fun onError(errorCode: Int, message: String) {
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            }
        }

        NaverIdLoginSDK.authenticate(this, oauthLoginCallback)
    }

    private fun getNaverProfile() {
        val nidProfileCallback = object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(result: NidProfileResponse) {
                Intent(this@LoginActivity, SignActivity::class.java).apply {
                    putExtra(LOGIN_EMAIL, result.profile?.email)
                    putExtra(LOGIN_REAL_NAME, result.profile?.name)
                    USER_LOGIN_TYPE = "네이버"
                    startActivity(this)
                }
            }

            override fun onError(errorCode: Int, message: String) {}

            override fun onFailure(httpStatus: Int, message: String) {}
        }

        NidOAuthLogin().callProfileApi(nidProfileCallback)
    }

    private fun setUpGoogleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode(BuildConfig.GOOGLE_CLIENT_ID)
            .requestIdToken(BuildConfig.GOOGLE_CLIENT_ID)
            .requestEmail().build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        signInLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(it.data)
                googleHandleResult(task)
            }

        binding.layoutGoogleLogin.setOnClickListener {
            var signIntent: Intent = mGoogleSignInClient.signInIntent
            signInLauncher.launch(signIntent)
        }
    }

    private fun googleHandleResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)

            viewModel.getAccessToken(account.serverAuthCode.toString())
            email = account.email.toString()
            displayName = account.displayName.toString()
        } catch (e: ApiException) {
            Log.e("test", "signInResult:failed Code = " + e.statusCode)
        }
    }

    private fun registerGoogleAccount(email: String, name: String) {
        Intent(this@LoginActivity, SignActivity::class.java).apply {
            putExtra(LOGIN_EMAIL, email)
            putExtra(LOGIN_REAL_NAME, name)
            startActivity(this)
        }
    }
}
