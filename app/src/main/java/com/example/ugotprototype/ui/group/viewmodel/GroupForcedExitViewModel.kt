package com.example.ugotprototype.ui.group.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.ApiService
import com.example.ugotprototype.data.api.GroupService
import com.example.ugotprototype.data.group.GroupDetailTeamInforData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupForcedExitViewModel @Inject constructor(private val groupService: GroupService, private val apiService: ApiService): ViewModel() {

    private val _teamPersonData = MutableLiveData<List<GroupDetailTeamInforData>>()
    val teamPersonData: MutableLiveData<List<GroupDetailTeamInforData>> = _teamPersonData

    private val _isUpdate = MutableLiveData<Boolean>()
    val isUpdate: MutableLiveData<Boolean> = _isUpdate

    private val _isLoadingPage = MutableLiveData<Boolean>()
    val isLoadingPage: LiveData<Boolean> = _isLoadingPage

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
        _isLoadingPage.value = false
        viewModelScope.launch {
            kotlin.runCatching {
                var personData = groupService.getGroupTeamPersonData(groupId)

                personData.forEach {
                    var githubNick =  Regex("github\\.com/([\\w-]+)").find(it.gitHubLink)?.groupValues?.get(1)
                    it.avatarUrl = apiService.getUser(githubNick.toString())?.avatar_url ?: ""
                }

               _teamPersonData.value = personData
            }.onSuccess {
                _isLoadingPage.value = true
            }.onFailure {
                _isLoadingPage.value = true
            }
        }
    }
}