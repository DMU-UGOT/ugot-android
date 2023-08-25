package com.example.ugotprototype.ui.group.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.group.GroupCalendarData
import com.example.ugotprototype.data.group.GroupEngagementData
import com.example.ugotprototype.data.group.GroupMiddleViewData
import com.example.ugotprototype.data.group.GroupTopViewData
import com.example.ugotprototype.data.api.ApiService
import com.example.ugotprototype.ui.group.view.GroupFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {
    private val _groupTopItemList = MutableLiveData<ArrayList<GroupTopViewData>>()
    val groupTopItemList: LiveData<ArrayList<GroupTopViewData>> = _groupTopItemList

    private val _groupMiddleItemList = MutableLiveData<ArrayList<GroupMiddleViewData>>()
    val groupMiddleItemList: LiveData<ArrayList<GroupMiddleViewData>> = _groupMiddleItemList

    private val _itemCount = MutableLiveData<Int>()
    val itemCount: LiveData<Int> = _itemCount

    private val _currentMonth = MutableLiveData<YearMonth>()
    val currentMonth: LiveData<YearMonth> = _currentMonth

    private val _groupNoticeDetail = MutableLiveData<ArrayList<GroupCalendarData>>()
    val groupNoticeDetail: LiveData<ArrayList<GroupCalendarData>> = _groupNoticeDetail

    private val _selectedDate = MutableLiveData<LocalDate>()
    val selectedDate: LiveData<LocalDate> = _selectedDate

    private val _isNoticeWriteButtonState = MutableLiveData<Boolean>()
    val isNoticeWriteButtonState: LiveData<Boolean> = _isNoticeWriteButtonState

    private val _bottomSheetClickCheck = MutableLiveData<Boolean>()
    val bottomSheetClickCheck: LiveData<Boolean> = _bottomSheetClickCheck

    private val _engagementRate = MutableLiveData<ArrayList<GroupEngagementData>>()
    val engagementRateData: LiveData<ArrayList<GroupEngagementData>> = _engagementRate

    private val _groupMaxPersonnel = MutableLiveData<Int>()
    var groupMaxPersonnel: LiveData<Int> = _groupMaxPersonnel

    private val _currentPage = MutableLiveData<Int>()
    val currentPage: LiveData<Int> = _currentPage

    fun setGroupTopData(groupTopData: ArrayList<GroupTopViewData>) {
        _groupTopItemList.value = groupTopData
    }

    fun setGroupMiddleData(groupMiddleData: ArrayList<GroupMiddleViewData>) {
        _groupMiddleItemList.value = groupMiddleData
    }

    fun setGroupMiddleItemCount(count: Int) {
        _itemCount.value = count
    }

    fun setCurrentMonth(month: YearMonth) {
        _currentMonth.value = month
    }

    fun setCalendarDataForDate(data: ArrayList<GroupCalendarData>) {
        _groupNoticeDetail.value = data
    }

    fun onDateClicked(date: LocalDate) {
        _selectedDate.value = date
    }

    fun isNoticeWriteStateButton(enabled: Boolean) {
        _isNoticeWriteButtonState.value = enabled
    }

    fun isBottomSheetClickCheck(enabled: Boolean) {
        _bottomSheetClickCheck.value = enabled
    }

    fun setEngagementRate(data: ArrayList<GroupEngagementData>) {
        _engagementRate.value = data
    }

    fun groupMaxPersonnel(maxPersonnel: Int) {
        _groupMaxPersonnel.value = maxPersonnel
    }

    fun getGroupList() {
        viewModelScope.launch {
            kotlin.runCatching {
                val avatarUrl = apiService.getOrganization(
                    "DMU-UGOT", "Bearer ${GroupFragment.TOKEN_DATA}"
                )?.avatarUrl
                Log.d("aaaaa1", avatarUrl.toString())
            }
        }
    }
}