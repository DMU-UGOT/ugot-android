package com.example.ugotprototype.ui.group.viewmodel

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
class GroupTeamInformationViewModel @Inject constructor(
    private val groupService: GroupService,
    private val apiService: ApiService
) : ViewModel() {
    private val _teamPersonData = MutableLiveData<List<GroupDetailTeamInforData>>()
    val teamPersonData: MutableLiveData<List<GroupDetailTeamInforData>> = _teamPersonData

    fun getTeamInformationData(groupId: Int) {
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
            }
        }
    }
}