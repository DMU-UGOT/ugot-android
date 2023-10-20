package com.example.ugotprototype.ui.login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.SharedPreference
import com.example.ugotprototype.data.api.OAuthService
import com.example.ugotprototype.data.oauth.RequestLoginNaver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val oauthLogin: OAuthService, private val sharedPreference: SharedPreference
) : ViewModel() {

    private val _userAccessToken = MutableLiveData<String?>()
    val userAccessToken: LiveData<String?> = _userAccessToken

    private val _kakaoUserAccessToken = MutableLiveData<String?>()
    val kakaoUserAccessToken: LiveData<String?> = _kakaoUserAccessToken

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
}