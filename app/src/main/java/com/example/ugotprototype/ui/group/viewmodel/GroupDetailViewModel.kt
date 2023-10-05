package com.example.ugotprototype.ui.group.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.SharedPreference
import com.example.ugotprototype.data.api.GroupService
import com.example.ugotprototype.data.api.ProfileService
import com.example.ugotprototype.data.group.GroupDetailData
import com.example.ugotprototype.data.group.GroupSetNoticeData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class GroupDetailViewModel @Inject constructor(
    private val groupService: GroupService,
    private val sharedPreference: SharedPreference,
    private val profileService: ProfileService
) : ViewModel() {
    private val _bottomSheetClickCheck = MutableLiveData<Boolean>()
    val bottomSheetClickCheck: LiveData<Boolean> = _bottomSheetClickCheck

    private val _isNoticeWriteButtonState = MutableLiveData<Boolean>()
    val isNoticeWriteButtonState: LiveData<Boolean> = _isNoticeWriteButtonState

    private val _groupDetailData = MutableLiveData<GroupDetailData>()
    val groupDetailData: LiveData<GroupDetailData> = _groupDetailData

    private val _isLoadingPage = MutableLiveData<Boolean>()
    val isLoadingPage: LiveData<Boolean> = _isLoadingPage

    private val _noticeData = MutableLiveData<GroupSetNoticeData>()
    val noticeData: LiveData<GroupSetNoticeData> = _noticeData

    private val _latestNotice = MutableLiveData<String>()
    val latestNotice: LiveData<String> = _latestNotice

    private val _isNoticeCreated = MutableLiveData<Boolean>()
    val isNoticeCreated: LiveData<Boolean> = _isNoticeCreated

    private val _isQuitGroup = MutableLiveData<Boolean>()
    val isQuitGroup: LiveData<Boolean> = _isQuitGroup

    private val _isDeleteGroup = MutableLiveData<Boolean>()
    val isDeleteGroup: LiveData<Boolean> = _isDeleteGroup

    private val _isGroupLeader = MutableLiveData<Boolean>()
    val isGroupLeder: LiveData<Boolean> = _isGroupLeader

    private val _isUpdateGroup = MutableLiveData<Boolean>()
    val isUpdateGroup: LiveData<Boolean> = _isUpdateGroup

    fun isBottomSheetClickCheck(enabled: Boolean) {
        _bottomSheetClickCheck.value = enabled
    }

    fun isNoticeWriteStateButton(enabled: Boolean) {
        _isNoticeWriteButtonState.value = enabled
    }

    fun getGroupDetailData(groupID: Int) {
        _isLoadingPage.value = false
        viewModelScope.launch {
            kotlin.runCatching {
                groupService.getGroupDetailData(groupID)
            }.onSuccess {
                _isLoadingPage.value = true
                _groupDetailData.value = it
            }.onFailure {
                _isLoadingPage.value = true
            }
        }
    }

    fun setNoticeData(noticeData: GroupSetNoticeData) {
        _noticeData.value = noticeData
    }

    fun sendNoticeData(groupId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                groupService.setGroupNotice(groupId, _noticeData.value!!)
            }.onSuccess {
                _isUpdateGroup.value = true
            }.onFailure {
                _isUpdateGroup.value = false
            }
        }
    }

    fun getNoticeData(groupId: Int) {
        var latestDate = ""
        var latestNoticeId = -1
        var latestContent = ""

        viewModelScope.launch {
            kotlin.runCatching {
                var groupResponse = groupService.getGroupNotice(groupId)
                val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())


                groupResponse.forEach {
                    val noticeDate = it.date
                    val noticeId = it.noticeId

                    if (noticeDate == currentDate && noticeId > latestNoticeId) {
                        latestDate = noticeDate
                        latestNoticeId = noticeId
                        latestContent = it.content
                    }
                }

                _latestNotice.value = latestContent
            }
        }
    }

    fun quitGroup(groupId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                groupService.quitGroup(groupId)
            }.onSuccess {
                _isQuitGroup.value = true
            }.onFailure {
                _isQuitGroup.value = false
            }
        }
    }

    fun deleteGroup(groupId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                groupService.deleteGroup(groupId)
            }.onSuccess {
                _isDeleteGroup.value = true
            }.onFailure {
                _isDeleteGroup.value = false
            }
        }
    }

    fun groupLeaderCheck(groupLeaderId: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            kotlin.runCatching {
                profileService.getUserInfo(sharedPreference.getMemberId().toString())
            }.onSuccess {
                if (groupLeaderId == it.nickname) {
                    callback(true)
                } else {
                    callback(false)
                }
            }
        }
    }
}