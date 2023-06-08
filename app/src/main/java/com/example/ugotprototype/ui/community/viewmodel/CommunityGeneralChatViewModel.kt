package com.example.ugotprototype.ui.community.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ugotprototype.data.community.CommunityGeneralChatViewData

class CommunityGeneralChatViewModel  : ViewModel() {
    private val _communityGeneralChatItemList = MutableLiveData<ArrayList<CommunityGeneralChatViewData>>() // 뷰 모델에서 데이터 처리를 하는 변수
    val communityGeneralChatItemList: LiveData<ArrayList<CommunityGeneralChatViewData>> = _communityGeneralChatItemList

    fun setCommunityGeneralChatData(communityGeneralChatViewData: ArrayList<CommunityGeneralChatViewData>) {
        _communityGeneralChatItemList.value = communityGeneralChatViewData
    }
}