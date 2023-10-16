package com.example.ugotprototype.ui.community.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.SharedPreference
import com.example.ugotprototype.data.api.ChangeService
import com.example.ugotprototype.data.api.MessageService
import com.example.ugotprototype.data.change.CommunityChangePostViewData
import com.example.ugotprototype.data.change.CommunityChangeRefreshData
import com.example.ugotprototype.data.change.CommunityMessageData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityChangeDetailViewModel @Inject constructor(
    private val changeService: ChangeService,
    private val messageService: MessageService,
    private val sharedPreference: SharedPreference
) : ViewModel() {
    private val _communityChangeDetailData = MutableLiveData<CommunityChangePostViewData>()
    val communityChangeDetailData: LiveData<CommunityChangePostViewData> =
        _communityChangeDetailData

    private val _sendMessageData = MutableLiveData<ArrayList<CommunityMessageData>>() // 뷰 모델에서 데이터 처리를 하는 변수
    val sendMessageData: LiveData<ArrayList<CommunityMessageData>> = _sendMessageData

    private val _dataRefresh = MutableLiveData<Boolean>()
    val dataRefresh: LiveData<Boolean> = _dataRefresh

    private val _isDeleteChangeGroup = MutableLiveData<Boolean>()
    val isDeleteChangeGroup: LiveData<Boolean> = _isDeleteChangeGroup

    private val _itemCount = MutableLiveData<Int>()
    val itemCount: LiveData<Int> = _itemCount

    fun setMessageData(messageData: ArrayList<CommunityMessageData>) {
        _sendMessageData.value = messageData
        _itemCount.value = messageData.size
    }

    fun getMessageList(postId:Int){
        viewModelScope.launch {
            kotlin.runCatching {
                val data = messageService.allMessage(postId)
                setMessageData(data)
            }
        }
    }

    fun getChangeDetailData(classChangeId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                changeService.getChangeDetail(classChangeId)
            }.onSuccess {
                _communityChangeDetailData.value = it
            }.onFailure {
                Log.d("fail","fail")
            }
        }
    }

    fun deleteChangeDetailText(classChangeId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                changeService.deleteChangeDetail(classChangeId)
            }.onSuccess {
                _isDeleteChangeGroup.value = true
            }.onFailure {
                _isDeleteChangeGroup.value = false
            }
        }
    }

    fun getLoggedInUserId(): Int {
        return sharedPreference.getMemberId()
    }

    fun refreshData(classChangeId: Int, communityChangeRefreshData: CommunityChangeRefreshData) {
        viewModelScope.launch {
            kotlin.runCatching {
                var data = changeService.refreshChange(classChangeId, communityChangeRefreshData)
            }.onSuccess { result ->
                _dataRefresh.value = true
            }.onFailure { exception ->
                _dataRefresh.value = false
            }
        }
    }
}