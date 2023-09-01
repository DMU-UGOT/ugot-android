package com.example.ugotprototype.ui.profile.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.SharedPreference
import com.example.ugotprototype.data.api.ApiService
import com.example.ugotprototype.data.api.ProfileService
import com.example.ugotprototype.data.profile.ProfileMemberData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolActivityViewModel @Inject constructor(
    private val profileService: ProfileService,
    private val sharedPreference: SharedPreference,
    private val apiService: ApiService
) : ViewModel() {

    private val _memberData = MutableLiveData<ProfileMemberData>()
    val memberData: MutableLiveData<ProfileMemberData> = _memberData

    private val _isMemberDataPatch = MutableLiveData<Boolean>()
    val isMemberDataPatch: MutableLiveData<Boolean> = _isMemberDataPatch

    private val _gitHubAccountValid = MutableLiveData<Boolean>()
    val gitHubAccountValid: MutableLiveData<Boolean> = _gitHubAccountValid

    fun initData() {
        viewModelScope.launch {
            kotlin.runCatching {
                profileService.getUserInfo(sharedPreference.getMemberId().toString())
            }.onSuccess {
                memberData.value = it
            }
        }
    }

    fun patchMemberData(memberData: ProfileMemberData) {
        viewModelScope.launch {
            kotlin.runCatching {
                profileService.setUserInfo(
                    sharedPreference.getMemberId().toString(), memberData
                )
            }.onSuccess {
                isMemberDataPatch.value = true
            }
        }
    }

    /*fun checkGitHubAccount(gitHubLink: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                apiService.getUser(gitHubLink)
            }.onSuccess {
                _gitHubAccountValid.value = true
            }.onFailure {
                _gitHubAccountValid.value = false
            }
        }
    }*/
}