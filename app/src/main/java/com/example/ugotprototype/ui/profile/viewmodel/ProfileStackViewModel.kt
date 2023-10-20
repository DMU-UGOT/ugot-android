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
class ProfileStackViewModel @Inject constructor(
    private val profileService: ProfileService, private val sharedPreference: SharedPreference
) : ViewModel() {

    private val _memberData = MutableLiveData<ProfileMemberData>()
    val memberData: MutableLiveData<ProfileMemberData> = _memberData

    private val _patchOk = MutableLiveData<Boolean>()
    val patchOk: MutableLiveData<Boolean> = _patchOk

    fun getSkillList() {
        viewModelScope.launch {
            kotlin.runCatching {
                profileService.getUserInfo(sharedPreference.getMemberId().toString())
            }.onSuccess {
                _memberData.value = it
            }
        }
    }

    fun updateSkillList(skill: List<String>) {
        viewModelScope.launch {
            kotlin.runCatching {
                _memberData.value?.skill = skill
                Log.d("test", _memberData.value.toString())
                profileService.setUserInfo(
                    sharedPreference.getMemberId().toString(),
                    memberData.value!!
                )
            }.onSuccess {
                patchOk.value = true
            }.onFailure {
                Log.d("test", it.toString())
            }
        }
    }
}