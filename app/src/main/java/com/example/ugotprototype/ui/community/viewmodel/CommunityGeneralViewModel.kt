package com.example.ugotprototype.ui.community.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ugotprototype.data.community.CommunityGeneralViewData

class CommunityGeneralViewModel : ViewModel() {
    private val _communityGeneralItemList = MutableLiveData<ArrayList<CommunityGeneralViewData>>() // 뷰 모델에서 데이터 처리를 하는 변수
    val communityGeneralItemList: LiveData<ArrayList<CommunityGeneralViewData>> = _communityGeneralItemList

    fun setCommunityGeneralData(communityGeneralViewData: ArrayList<CommunityGeneralViewData>) {
        _communityGeneralItemList.value = communityGeneralViewData
    }
}