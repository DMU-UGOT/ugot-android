package com.example.ugotprototype.ui.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ugotprototype.data.profile.ProfileMessageData
import com.example.ugotprototype.data.profile.ProfileMessageSendData

class ProfileMessageViewModel : ViewModel() {
    private val _profileMessageItemList =
        MutableLiveData<ArrayList<ProfileMessageData>>() // 뷰 모델에서 데이터 처리를 하는 변수
    val profileMessageItemList: LiveData<ArrayList<ProfileMessageData>> =
        _profileMessageItemList

    fun setProfileMessageData(profileMessageData: ArrayList<ProfileMessageData>) {
        _profileMessageItemList.value = profileMessageData
    }
}