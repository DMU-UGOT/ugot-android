package com.example.ugotprototype.ui.group.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.ApiService
import com.example.ugotprototype.data.api.GroupService
import com.example.ugotprototype.data.group.GroupEngagementData
import com.example.ugotprototype.data.group.GroupGetFavoritesList
import com.example.ugotprototype.data.group.GroupMiddleViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val apiService: ApiService,
    private val groupService: GroupService
) : ViewModel() {

    private val _groupMiddleItemList = MutableLiveData<List<GroupMiddleViewData>>()
    val groupMiddleItemList: LiveData<List<GroupMiddleViewData>> = _groupMiddleItemList

    private val _itemCount = MutableLiveData<Int>()
    val itemCount: LiveData<Int> = _itemCount

    private val _engagementRate = MutableLiveData<ArrayList<GroupEngagementData>>()
    val engagementRateData: LiveData<ArrayList<GroupEngagementData>> = _engagementRate

    private val _groupMaxPersonnel = MutableLiveData<Int>()
    var groupMaxPersonnel: LiveData<Int> = _groupMaxPersonnel

    private val _groupCount = MutableLiveData<Int>()
    val groupCount: LiveData<Int> = _groupCount

    private val _isLoadingPage = MutableLiveData<Boolean>()
    val isLoadingPage: LiveData<Boolean> = _isLoadingPage

    private val _addFavorites = MutableLiveData<Boolean>()
    val addFavorites: LiveData<Boolean> = _addFavorites

    private val _getFavoritesList = MutableLiveData<List<GroupGetFavoritesList>>()
    val getFavoritesList: LiveData<List<GroupGetFavoritesList>> = _getFavoritesList

    fun setEngagementRate(data: ArrayList<GroupEngagementData>) {
        _engagementRate.value = data
    }

    fun getGroupList() {
        _isLoadingPage.value = false
        viewModelScope.launch {
            kotlin.runCatching {
                var groupResponse = groupService.getGroupData()

                val isFavorites = groupService.getGroupFavorites()
                val groupId = isFavorites.map { it.groupId }

                groupResponse.forEach {
                    it.isFavorites = it.groupId in groupId
                    kotlin.runCatching {
                        it.avatarUrl = apiService.getOrganization(it.githubUrl)?.avatarUrl ?: ""
                    }
                }

                _groupMiddleItemList.value = groupResponse
                _groupCount.value = _groupMiddleItemList.value?.size ?: 0
            }.onSuccess {
                _isLoadingPage.value = true
            }.onFailure {
                _isLoadingPage.value = true
            }
        }
    }

    fun setGroupFavorites(groupId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                groupService.setGroupFavorites(groupId)
            }.onSuccess {
                Log.d("test", "성공")
                _addFavorites.value = true
            }.onFailure {
                Log.d("test", it.toString())
                _addFavorites.value = false
            }
        }
    }

    fun getFavoritesList() {
        viewModelScope.launch {
            kotlin.runCatching {
                val groupResponse = groupService.getGroupFavorites()

                groupResponse.forEach {
                    kotlin.runCatching {
                        it.avatarUrl = apiService.getOrganization(it.gitHubUrl)?.avatarUrl ?: ""
                    }
                }

                _getFavoritesList.value = groupResponse
            }
        }
    }
}