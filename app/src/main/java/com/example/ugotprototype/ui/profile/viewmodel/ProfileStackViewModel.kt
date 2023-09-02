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
class ProfileStackViewModel @Inject constructor(
    private val profileService: ProfileService, private val sharedPreference: SharedPreference
) : ViewModel() {

    private val _skillList = MutableLiveData<List<String>>()
    val skillList: MutableLiveData<List<String>> = _skillList

    fun getSkillList() {
        viewModelScope.launch {
            kotlin.runCatching {
                profileService.getUserInfo(sharedPreference.getMemberId().toString())
            }.onSuccess {
                _skillList.value = it.skill
            }
        }
    }

}