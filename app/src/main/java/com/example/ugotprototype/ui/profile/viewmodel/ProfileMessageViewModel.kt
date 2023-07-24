package com.example.ugotprototype.ui.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ugotprototype.data.profile.ProfileMessageData

class ProfileMessageViewModel : ViewModel() {
    private val _profileMessageItemList =
        MutableLiveData<ArrayList<ProfileMessageData>>() // 뷰 모델에서 데이터 처리를 하는 변수
    val profileMessageItemList: LiveData<ArrayList<ProfileMessageData>> =
        _profileMessageItemList

    private val _itemCount = MutableLiveData<Int>()
    val itemCount: LiveData<Int> = _itemCount


    fun setProfileMessageData(profileMessageData: ArrayList<ProfileMessageData>) {
        _profileMessageItemList.value = profileMessageData
        _itemCount.value = profileMessageData.size
    }
}