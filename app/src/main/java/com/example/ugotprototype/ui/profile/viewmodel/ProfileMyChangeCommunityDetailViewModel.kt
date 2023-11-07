package com.example.ugotprototype.ui.profile.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.SharedPreference
import com.example.ugotprototype.data.api.MessageService
import com.example.ugotprototype.data.api.ProfileService
import com.example.ugotprototype.data.change.CommunityChangePostViewData
import com.example.ugotprototype.data.change.CommunityChangeRefreshData
import com.example.ugotprototype.data.change.CommunityMessageData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileMyChangeCommunityDetailViewModel @Inject constructor(
    private val profileService: ProfileService,
    private val messageService: MessageService,
    private val sharedPreference: SharedPreference
) : ViewModel() {
    private val _profileChangeDetailData = MutableLiveData<CommunityChangePostViewData>()
    val profileChangeDetailData: LiveData<CommunityChangePostViewData> =
        _profileChangeDetailData

    private val _sendMessageData = MutableLiveData<ArrayList<CommunityMessageData>>()
    val sendMessageData: LiveData<ArrayList<CommunityMessageData>> = _sendMessageData

    private val _dataRefresh = MutableLiveData<Boolean>()
    val dataRefresh: LiveData<Boolean> = _dataRefresh

    private val _isDeleteChangeGroup = MutableLiveData<Boolean>()
    val isDeleteChangeGroup: LiveData<Boolean> = _isDeleteChangeGroup

    fun sendMessage(communityId : Int, messageData: CommunityMessageData) {
        viewModelScope.launch {
            kotlin.runCatching {
                messageService.sendMessage(communityId, messageData)
            }.onSuccess {
                Log.d("success","success")
            }.onFailure {
                Log.d("fail","fail")
            }
        }
    }

    fun getChangeDetailData(classChangeId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                profileService.getChangeDetail(classChangeId)
            }.onSuccess {
                _profileChangeDetailData.value = it
            }.onFailure {
                Log.d("fail","fail")
            }
        }
    }

    fun deleteChangeDetailText(classChangeId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                profileService.deleteChangeDetail(classChangeId)
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
                var data = profileService.refreshChange(classChangeId, communityChangeRefreshData)
            }.onSuccess { result ->
                _dataRefresh.value = true
            }.onFailure { exception ->
                _dataRefresh.value = false
            }
        }
    }
}