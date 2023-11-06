package com.example.ugotprototype.ui.profile.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.SharedPreference
import com.example.ugotprototype.data.api.ProfileService
import com.example.ugotprototype.data.profile.ProfileMemberData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileFragmentViewModel @Inject constructor(
    private val profileService: ProfileService, private val sharedPreference: SharedPreference
) : ViewModel() {

    private val _isDeleteAccount = MutableLiveData<Boolean>()
    val isDeleteAccount: MutableLiveData<Boolean> = _isDeleteAccount

    private val _profileMemberData = MutableLiveData<ProfileMemberData>()
    val profileMemberData: MutableLiveData<ProfileMemberData> = _profileMemberData

    fun deleteUser() {
        viewModelScope.launch {
            kotlin.runCatching {
                profileService.deleteUser(sharedPreference.getMemberId().toString())
            }.onSuccess {
                sharedPreference.saveMemberId(0)
                sharedPreference.saveToken("")
                isDeleteAccount.value = true
                sharedPreference.saveAutoLogin(false)
            }.onFailure {
                Log.d("test", it.toString())
                Log.d("test", sharedPreference.getMemberId().toString())
                isDeleteAccount.value = false
            }
        }
    }

    fun profileGetUserInfo() {
        viewModelScope.launch {
            kotlin.runCatching {
                profileService.getUserInfo(sharedPreference.getMemberId().toString())
            }.onSuccess {
                _profileMemberData.value = it
            }
        }
    }
}