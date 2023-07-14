package com.example.ugotprototype.ui.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ugotprototype.data.profile.ProfileMessageDetailData

class ProfileMessageDetailViewModel : ViewModel() {
    private val _profileMessageDetailItemList =
        MutableLiveData<ArrayList<ProfileMessageDetailData>>() // 뷰 모델에서 데이터 처리를 하는 변수
    val profileMessageDetailItemList: LiveData<ArrayList<ProfileMessageDetailData>> =
        _profileMessageDetailItemList

    fun setProfileMessageDetailData(profileMessageDetailData: ArrayList<ProfileMessageDetailData>) {
        _profileMessageDetailItemList.value = profileMessageDetailData
    }
}