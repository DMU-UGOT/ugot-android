package com.example.ugotprototype.ui.group.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.GroupService
import com.example.ugotprototype.data.group.GroupDetailTeamInforData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupForcedExitViewModel @Inject constructor(private val groupService: GroupService): ViewModel() {

    private val _teamPersonData = MutableLiveData<List<GroupDetailTeamInforData>>()
    val teamPersonData: MutableLiveData<List<GroupDetailTeamInforData>> = _teamPersonData

    private val _isUpdate = MutableLiveData<Boolean>()
    val isUpdate: MutableLiveData<Boolean> = _isUpdate
    fun groupForcedExit(groupId: Int, applicationId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                groupService.groupForcedExit(groupId, applicationId)
            }.onSuccess {
                _isUpdate.value = true
            }
        }
    }

    fun getGroupPersonList(groupId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                groupService.getGroupTeamPersonData(groupId)
            }.onSuccess {
                _teamPersonData.value = it
            }
        }
    }
}