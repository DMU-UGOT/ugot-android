package com.example.ugotprototype.ui.profile.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.SharedPreference
import com.example.ugotprototype.data.api.ProfileService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileFragmentViewModel @Inject constructor(
    private val profileService: ProfileService, private val sharedPreference: SharedPreference
) : ViewModel() {

    private val _isDeleteAccount = MutableLiveData<Boolean>()
    val isDeleteAccount: MutableLiveData<Boolean> = _isDeleteAccount

    fun deleteUser() {
        viewModelScope.launch {
            kotlin.runCatching {
                profileService.deleteUser(sharedPreference.getMemberId().toString())
            }.onSuccess {
                isDeleteAccount.value = true
                sharedPreference.saveAutoLogin(false)
                sharedPreference.saveMemberId(0)
                sharedPreference.saveToken("")
            }.onFailure {
                    Log.d("계정이 안지워짐", it.toString())
                    isDeleteAccount.value = false
                }
        }
    }
}