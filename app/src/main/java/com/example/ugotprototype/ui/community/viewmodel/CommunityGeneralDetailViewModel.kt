package com.example.ugotprototype.ui.community.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.SharedPreference
import com.example.ugotprototype.data.community.CommunityGeneralPostViewData
import com.example.ugotprototype.data.community.CommunityGeneralRefreshData
import com.example.ugotprototype.di.api.CommunityService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityGeneralDetailViewModel @Inject constructor(
    private val communityService: CommunityService,
    private val sharedPreference: SharedPreference
) : ViewModel() {
    private val _communityDetailData = MutableLiveData<CommunityGeneralPostViewData>()
    val communityDetailData: LiveData<CommunityGeneralPostViewData> = _communityDetailData

    private val _dataRefresh = MutableLiveData<Boolean>()
    val dataRefresh: LiveData<Boolean> = _dataRefresh

    fun getCommunityDetailList(postId:Int){
        viewModelScope.launch {
            kotlin.runCatching {
                val data = communityService.getCommunityDetailGeneral(postId)
                _communityDetailData.value = data
            }
        }
    }

    fun deleteDetailText(postId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                communityService.deleteCommunity(postId)
            }.onSuccess {
                Log.d("delete", "삭제완료")
            }.onFailure {
                Log.d("삭제 실패", it.toString())
                Log.d("deleteX", it.toString())
            }
        }
    }

    // 현재 로그인된 사용자의 ID를 가져오는 함수
    fun getLoggedInUserId(): Int {
        return sharedPreference.getMemberId()
    }

    fun refreshData(postId: Int, communityGeneralRefreshData: CommunityGeneralRefreshData){
        viewModelScope.launch {
            kotlin.runCatching {
                var data = communityService.refreshCommunity(postId, communityGeneralRefreshData)
            }.onSuccess { result ->
                _dataRefresh.value = true}
                .onFailure { exception ->
                    _dataRefresh.value = false
                }
        }
    }
}