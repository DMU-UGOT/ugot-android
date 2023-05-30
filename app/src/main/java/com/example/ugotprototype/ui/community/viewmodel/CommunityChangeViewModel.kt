package com.example.ugotprototype.ui.community.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ugotprototype.data.community.CommunityChangeViewData

class CommunityChangeViewModel : ViewModel() {
    private val _communityChangeItemList = MutableLiveData<ArrayList<CommunityChangeViewData>>() // 뷰 모델에서 데이터 처리를 하는 변수
    val communityChangeItemList: LiveData<ArrayList<CommunityChangeViewData>> = _communityChangeItemList

    fun setCommunityChangeData(communityChangeViewData: ArrayList<CommunityChangeViewData>) {
        _communityChangeItemList.value = communityChangeViewData
    }
}