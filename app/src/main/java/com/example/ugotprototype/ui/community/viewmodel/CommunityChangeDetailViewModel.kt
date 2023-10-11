package com.example.ugotprototype.ui.community.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.SharedPreference
import com.example.ugotprototype.data.api.ChangeService
import com.example.ugotprototype.data.change.CommunityChangePostViewData
import com.example.ugotprototype.data.change.CommunityChangeRefreshData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityChangeDetailViewModel @Inject constructor(
    private val changeService: ChangeService,
    private val sharedPreference: SharedPreference
) : ViewModel() {
    private val _communityChangeDetailData = MutableLiveData<CommunityChangePostViewData>()
    val communityChangeDetailData: LiveData<CommunityChangePostViewData> =
        _communityChangeDetailData

    private val _dataRefresh = MutableLiveData<Boolean>()
    val dataRefresh: LiveData<Boolean> = _dataRefresh

    private val _isDeleteChangeGroup = MutableLiveData<Boolean>()
    val isDeleteChangeGroup: LiveData<Boolean> = _isDeleteChangeGroup

    fun getCommunityChangeDetailList(classChangeId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                val data = changeService.getChangeDetail(classChangeId)
                _communityChangeDetailData.value = data
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