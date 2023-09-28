package com.example.ugotprototype.ui.group.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.SharedPreference
import com.example.ugotprototype.data.api.GroupService
import com.example.ugotprototype.data.group.GroupGetNotice
import com.example.ugotprototype.data.group.GroupGetUserInfo
import com.example.ugotprototype.data.group.GroupOneDayNoticeData
import com.example.ugotprototype.data.group.GroupSetNoticeData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class GroupCalendarViewModel @Inject constructor(
    private val groupService: GroupService, private val sharedPreference: SharedPreference
) : ViewModel() {
    private val _currentMonth = MutableLiveData<YearMonth>()
    val currentMonth: LiveData<YearMonth> = _currentMonth

    private val _groupNoticeDetail = MutableLiveData<ArrayList<GroupGetNotice>>()
    val groupNoticeDetail: LiveData<ArrayList<GroupGetNotice>> = _groupNoticeDetail

    private val _selectedDate = MutableLiveData<LocalDate>()
    val selectedDate: LiveData<LocalDate> = _selectedDate

    private val _currentPage = MutableLiveData<Int>()
    val currentPage: LiveData<Int> = _currentPage

    private val _noticeTitle = MutableLiveData<Map<LocalDate, String>>()
    val noticeTitle: LiveData<Map<LocalDate, String>> = _noticeTitle

    private val _oneDayNoticeData = MutableLiveData<List<GroupOneDayNoticeData>>()
    val oneDayNoticeData: LiveData<List<GroupOneDayNoticeData>> = _oneDayNoticeData

    private val _getUserInfo = MutableLiveData<GroupGetUserInfo>()
    val getUserInfo: LiveData<GroupGetUserInfo> = _getUserInfo

    private val _isUpdateNotice = MutableLiveData<Boolean>()
    val isUpdateNotice: LiveData<Boolean> = _isUpdateNotice

    private val _isDeleteNotice = MutableLiveData<Boolean>()
    val isDeleteNotice: LiveData<Boolean> = _isDeleteNotice

    fun setCurrentMonth(month: YearMonth) {
        _currentMonth.value = month
    }

    fun onDateClicked(date: LocalDate) {
        _selectedDate.value = date
    }

    fun getNoticeData(groupId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                groupService.getCalendarGroupNotice(groupId)
            }.onSuccess {
                _groupNoticeDetail.value = it
            }.onFailure {}
        }
    }

    fun updateNoticeTitle(newTitle: ArrayList<GroupGetNotice>) {
        _noticeTitle.value = newTitle.groupBy {
            LocalDate.parse(it.date)
        }.mapValues { entry -> entry.value.last().content }
    }

    fun getClickDateData(localDate: String) {
        val newDataList = mutableListOf<GroupOneDayNoticeData>()

        _groupNoticeDetail.value?.forEach {
            if (it.date == localDate) {
                val oneDayNoticeData = GroupOneDayNoticeData(
                    noticeId = it.noticeId, date = it.date, content = it.content
                )
                newDataList.add(oneDayNoticeData)
            }
        }
        Log.d("test", newDataList.toString())
        _oneDayNoticeData.value = newDataList
    }

    fun groupLeaderCheck(groupLeaderId: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            kotlin.runCatching {
                groupService.getUserInfo(sharedPreference.getMemberId())
            }.onSuccess {
                if (it.nickname == groupLeaderId) {
                    callback(true)
                } else {
                    callback(false)
                }
            }.onFailure {
                callback(false)
            }
        }
    }

    fun updateNotice(
        text: List<String>, text2: String, groupId: Int, noticeId: Int, callback: (Boolean) -> Unit
    ) {
        val data = GroupSetNoticeData(
            year = text[0], month = text[1], dateOfMonth = text[2], content = text2
        )
        viewModelScope.launch {
            kotlin.runCatching {
                groupService.updateNotice(groupId, noticeId, data)
            }.onSuccess {
                _isUpdateNotice.value = true
                callback(true)
            }.onFailure {
                _isUpdateNotice.value = false
                callback(false)
            }
        }
    }

    fun deleteNotice(noticeId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                groupService.deleteNotice(noticeId)
            }.onSuccess {
                _isUpdateNotice.value = true
            }.onFailure {
                _isUpdateNotice.value = false
            }
        }
    }
}