package com.example.ugotprototype.ui.group.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.ApiService
import com.example.ugotprototype.data.api.GroupService
import com.example.ugotprototype.data.group.GroupGetApplicationList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupRequestApplicationViewModel @Inject constructor(private val groupService: GroupService, private val apiService: ApiService) :
    ViewModel() {
    private val _groupRequestApplicationList = MutableLiveData<List<GroupGetApplicationList>>()
    val groupRequestApplicationList: LiveData<List<GroupGetApplicationList>> =
        _groupRequestApplicationList

    private val _isUpdate = MutableLiveData<Boolean>()
    val isUpdate: LiveData<Boolean> = _isUpdate

    private val _isLoadingPage = MutableLiveData<Boolean>()
    val isLoadingPage: LiveData<Boolean> = _isLoadingPage

    fun getGroupApplicationList(groupId: Int) {
        _isLoadingPage.value = false
        viewModelScope.launch {
            kotlin.runCatching {
                var personData = groupService.getApplicationList(groupId)

                personData.forEach {
                    var githubNick =  Regex("github\\.com/([\\w-]+)").find(it.gitHubLink)?.groupValues?.get(1)
                    it.avatarUrl = apiService.getUser(githubNick.toString())?.avatar_url ?: ""
                }

                _groupRequestApplicationList.value = personData
            }.onSuccess {
                _isLoadingPage.value = true
            }.onFailure {
                _isLoadingPage.value = true
            }
        }
    }

    fun receiveApplication(groupId: Int, applicationId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                groupService.receiveApplication(groupId, applicationId)
            }.onSuccess {
                _isUpdate.value = true
            }.onFailure {
                _isUpdate.value = false
            }
        }
    }

    fun rejectApplication(groupId: Int, applicationId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                groupService.rejectApplication(groupId, applicationId)
            }.onSuccess {
                _isUpdate.value = true
            }.onFailure {
                _isUpdate.value = false
            }
        }
    }
}