package com.example.ugotprototype.ui.login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.BuildConfig
import com.example.ugotprototype.SharedPreference
import com.example.ugotprototype.data.api.OAuthService
import com.example.ugotprototype.data.oauth.LoginGoogleRequestModel
import com.example.ugotprototype.data.oauth.LoginGoogleResponseModel
import com.example.ugotprototype.data.oauth.RequestLoginNaver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val oauthLogin: OAuthService, private val sharedPreference: SharedPreference
) : ViewModel() {

    private val _userAccessToken = MutableLiveData<String?>()
    val userAccessToken: LiveData<String?> = _userAccessToken

    private val _kakaoUserAccessToken = MutableLiveData<String?>()
    val kakaoUserAccessToken: LiveData<String?> = _kakaoUserAccessToken

    private val _auserAccessToken = MutableLiveData<String?>()
    val auserAccessToken: LiveData<String?> = _auserAccessToken

    private val _buserAccessToken = MutableLiveData<String?>()
    val buserAccessToken: LiveData<String?> = _buserAccessToken

    fun loginNaver(accessToken: RequestLoginNaver) {
        viewModelScope.launch {
            kotlin.runCatching {
                oauthLogin.loginNaver(accessToken)
            }.onSuccess {
                sharedPreference.saveMemberId(it.data.tokenInfo?.memberId ?: 0)
                _userAccessToken.value = it.data.tokenInfo?.accessToken ?: ""
            }.onFailure {
                Log.e("loginError", it.toString())
            }
        }
    }

    fun loginKakao(accessToken: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            kotlin.runCatching {
                oauthLogin.loginKakao(accessToken)
            }.onSuccess {
                if (it.data.accessToken == "" || it.data.tokenInfo?.accessToken == null) {
                    callback(true)
                } else {
                    sharedPreference.saveMemberId(it.data.tokenInfo?.memberId ?: 0)
                    _kakaoUserAccessToken.value = it.data.tokenInfo!!.accessToken
                    callback(false)
                }
            }.onFailure {
                callback(true)
            }
        }
    }

    fun loginGoogle(accessToken: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                oauthLogin.loginGoogle(accessToken)
            }.onSuccess {
                sharedPreference.saveMemberId(it.data.tokenInfo?.memberId ?: 0)
                _buserAccessToken.value = it.data.tokenInfo?.accessToken
            }
        }
    }

    fun getAccessToken(authCode:String) {
        viewModelScope.launch {
            kotlin.runCatching {
                OAuthService.loginRetrofit("https://www.googleapis.com").getAccessToken(
                    request = LoginGoogleRequestModel(
                        grant_type = "authorization_code",
                        client_id = BuildConfig.GOOGLE_CLIENT_ID,
                        client_secret = BuildConfig.GOOGLE_CLIENT_SECRET,
                        code = authCode.orEmpty()
                    )
                ).enqueue(object : retrofit2.Callback<LoginGoogleResponseModel> {
                    override fun onResponse(call: Call<LoginGoogleResponseModel>, response: Response<LoginGoogleResponseModel>) {
                        if(response.isSuccessful) {
                            Log.d("test", response.body()?.access_token.orEmpty())
                            _auserAccessToken.value = response.body()?.access_token.orEmpty()
                        }
                    }
                    override fun onFailure(call: Call<LoginGoogleResponseModel>, t: Throwable) {
                        Log.d("getOnFailure: ", t.fillInStackTrace().toString())
                    }
                })
            }
        }
    }
}