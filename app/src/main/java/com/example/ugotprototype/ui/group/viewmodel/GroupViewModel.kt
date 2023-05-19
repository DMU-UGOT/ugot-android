package com.example.ugotprototype.ui.group.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ugotprototype.data.group.GroupMiddleViewData
import com.example.ugotprototype.data.group.GroupTopViewData

class GroupViewModel : ViewModel() {
    private val _groupTopItemList = MutableLiveData<ArrayList<GroupTopViewData>>()
    val groupTopItemList: LiveData<ArrayList<GroupTopViewData>> = _groupTopItemList

    private val _groupMiddleItemList = MutableLiveData<ArrayList<GroupMiddleViewData>>()
    val groupMiddleItemList: LiveData<ArrayList<GroupMiddleViewData>> = _groupMiddleItemList

    private val _itemCount = MutableLiveData<Int>()
    val itemCount: LiveData<Int> = _itemCount

    fun setGroupTopData(groupTopData: ArrayList<GroupTopViewData>) {
        _groupTopItemList.value = groupTopData
    }

    fun setGroupMiddleData(groupMiddleData: ArrayList<GroupMiddleViewData>) {
        _groupMiddleItemList.value = groupMiddleData
    }

    fun setGroupMiddleItemCount(count: Int) {
        _itemCount.value = count
    }
}