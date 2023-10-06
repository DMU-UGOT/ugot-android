package com.example.ugotprototype.ui.group.viewmodel

import android.util.Log
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
class GroupHandOverViewModel @Inject constructor(
    private val groupService: GroupService, private val apiService: ApiService
) : ViewModel() {
    private val _teamPersonData = MutableLiveData<List<GroupDetailTeamInforData>>()
    val teamPersonData: MutableLiveData<List<GroupDetailTeamInforData>> = _teamPersonData

    private val _isLoadingPage = MutableLiveData<Boolean>()
    val isLoadingPage: LiveData<Boolean> = _isLoadingPage

    private val _isHandOver = MutableLiveData<Boolean>()
    val isHandOver: LiveData<Boolean> = _isHandOver

    fun getTeamInformationData(groupId: Int) {
        _isLoadingPage.value = false
        viewModelScope.launch {
            kotlin.runCatching {
                var groupResponse = groupService.getGroupTeamPersonData(groupId)

                groupResponse.forEach {
                    kotlin.runCatching {
                        var githubNick =
                            Regex("github\\.com/([\\w-]+)").find(it.gitHubLink)?.groupValues?.get(1)
                        it.avatarUrl = apiService.getUser(githubNick.toString())?.avatar_url ?: ""
                    }
                }

                _teamPersonData.value = groupResponse
            }.onSuccess {
                _isLoadingPage.value = true
            }.onFailure {
                _isLoadingPage.value = true
            }
        }
    }

    fun handOverLeader(groupId: Int, memberId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                groupService.handOverLeader(groupId, memberId)
            }.onSuccess {
                _isHandOver.value = true
            }.onFailure {
                Log.d("test", it.toString())
            }
        }
    }
}
