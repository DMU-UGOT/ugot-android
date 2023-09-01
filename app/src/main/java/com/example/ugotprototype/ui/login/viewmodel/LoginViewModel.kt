package com.example.ugotprototype.ui.login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.OAuthService
import com.example.ugotprototype.data.oauth.RequestLoginNaver
import com.example.ugotprototype.SharedPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val oauthLogin: OAuthService,
    private val sharedPreference: SharedPreference
) : ViewModel() {

    private val _userAccessToken = MutableLiveData<String?>()
    val userAccessToken: LiveData<String?> = _userAccessToken

    fun loginNaver(accessToken: RequestLoginNaver) {
        viewModelScope.launch {
            kotlin.runCatching {
                oauthLogin.loginNaver(accessToken)
            }.onSuccess {
                sharedPreference.saveMemberId(it.data.tokenInfo?.memberId ?: 0)
                _userAccessToken.value = it.data.tokenInfo?.accessToken?:""
            }.onFailure {
                Log.e("loginError", it.toString())
            }
        }
    }
}