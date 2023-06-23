package com.example.ugotprototype.ui.group.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ugotprototype.data.group.GroupCommunityChatViewData

class GroupCmuChatViewModel : ViewModel() {
    private val _groupCmuChatItemList = MutableLiveData<ArrayList<GroupCommunityChatViewData>>() // 뷰 모델에서 데이터 처리를 하는 변수
    val groupCmuChatItemList: LiveData<ArrayList<GroupCommunityChatViewData>> = _groupCmuChatItemList

    fun setGroupCmuChatData(groupCmuChatViewData: ArrayList<GroupCommunityChatViewData>) {
        _groupCmuChatItemList.value = groupCmuChatViewData
    }
}