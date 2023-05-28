package com.example.ugotprototype.ui.group.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ugotprototype.data.group.GroupCalendarData
import com.example.ugotprototype.data.group.GroupMiddleViewData
import com.example.ugotprototype.data.group.GroupTopViewData
import java.time.LocalDate
import java.time.YearMonth

class GroupViewModel : ViewModel() {
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
}