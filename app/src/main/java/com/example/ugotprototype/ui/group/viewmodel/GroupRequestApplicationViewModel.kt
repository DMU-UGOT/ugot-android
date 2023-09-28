package com.example.ugotprototype.ui.group.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.GroupService
import com.example.ugotprototype.data.group.GroupGetApplicationList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupRequestApplicationViewModel @Inject constructor(private val groupService: GroupService) :
    ViewModel() {
    private val _groupRequestApplicationList = MutableLiveData<List<GroupGetApplicationList>>()
    val groupRequestApplicationList: LiveData<List<GroupGetApplicationList>> =
        _groupRequestApplicationList

    private val _isUpdate = MutableLiveData<Boolean>()
    val isUpdate: LiveData<Boolean> = _isUpdate

    fun getGroupApplicationList(groupId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                groupService.getApplicationList(groupId)
            }.onSuccess {
                _groupRequestApplicationList.value = it
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