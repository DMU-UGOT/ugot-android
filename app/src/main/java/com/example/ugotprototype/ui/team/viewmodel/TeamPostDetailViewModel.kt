package com.example.ugotprototype.ui.team.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.SharedPreference
import com.example.ugotprototype.data.api.GroupService
import com.example.ugotprototype.data.api.ProfileService
import com.example.ugotprototype.data.api.TeamBuildingService
import com.example.ugotprototype.data.response.Team
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamPostDetailViewModel @Inject constructor(
    private val groupService: GroupService,
    private val teamBuildingService: TeamBuildingService,
    private val profileService: ProfileService,
    private val sharedPreference: SharedPreference
) : ViewModel() {

    private val _teamDetailData = MutableLiveData<Team>()
    val teamDetailData: LiveData<Team> = _teamDetailData

    private val _isDuplicateGroupPerson = MutableLiveData<Boolean>()
    val isDuplicateGroupPerson: LiveData<Boolean> = _isDuplicateGroupPerson

    private val _isPostDelete = MutableLiveData<Boolean>()
    val isPostDelete: LiveData<Boolean> = _isPostDelete

    fun sendApplication(groupId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                groupService.sendApplication(groupId)
            }.onSuccess {
                _isDuplicateGroupPerson.value = true
            }.onFailure {
                _isDuplicateGroupPerson.value = false
            }
        }
    }

    fun viewSetting(teamId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                teamBuildingService.getTeam(teamId)
            }.onSuccess {
                _teamDetailData.value = it
            }
        }
    }

    fun getMyNickName(callback: (String) -> Unit) {
        viewModelScope.launch {
            kotlin.runCatching {
                profileService.getUserInfo(sharedPreference.getMemberId().toString())
            }.onSuccess {
                callback(it.nickname)
            }.onFailure {
                callback("")
            }
        }
    }

    fun deleteMyPost(postId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                profileService.deleteTeamPost(postId)
            }.onSuccess {
                _isPostDelete.value = true
            }.onFailure {
                _isPostDelete.value = false
            }
        }
    }
}