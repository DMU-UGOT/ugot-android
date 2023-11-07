package com.example.ugotprototype.ui.profile.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.SharedPreference
import com.example.ugotprototype.data.api.ProfileService
import com.example.ugotprototype.data.community.CommunityGeneralPostViewData
import com.example.ugotprototype.data.community.CommunityGeneralRefreshData
import com.example.ugotprototype.di.api.CommunityService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileMyGeneralCommunityDetailViewModel @Inject constructor(
    private val profileService: ProfileService,
    private val sharedPreference: SharedPreference
) : ViewModel() {
    private val _communityDetailData = MutableLiveData<CommunityGeneralPostViewData>()
    val communityDetailData: LiveData<CommunityGeneralPostViewData> = _communityDetailData

    private val _dataRefresh = MutableLiveData<Boolean>()
    val dataRefresh: LiveData<Boolean> = _dataRefresh

    private val _isDeleteGeneralGroup = MutableLiveData<Boolean>()
    val isDeleteGeneralGroup: LiveData<Boolean> = _isDeleteGeneralGroup

    fun getLoggedInUserId(): Int {
        return sharedPreference.getMemberId()
    }

    fun getCommunityDetailList(postId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                profileService.getProfileGeneralDetail(postId)
            }.onSuccess {
                _communityDetailData.value = it
            }.onFailure {
                Log.d("fail","fail")
            }
        }
    }

    fun deleteDetailText(postId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                profileService.deleteGeneralCommunity(postId)
            }.onSuccess {
                _isDeleteGeneralGroup.value = true
            }.onFailure {
                _isDeleteGeneralGroup.value = false
            }
        }
    }

    fun refreshData(postId: Int, communityGeneralRefreshData: CommunityGeneralRefreshData){
        viewModelScope.launch {
            kotlin.runCatching {
                var data = profileService.refreshProfileGeneralDetail(postId, communityGeneralRefreshData)
            }.onSuccess { result ->
                _dataRefresh.value = true}
                .onFailure { exception ->
                    _dataRefresh.value = false
                }
        }
    }
}