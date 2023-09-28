package com.example.ugotprototype.ui.team.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.GroupService
import com.example.ugotprototype.data.api.TeamBuildingService
import com.example.ugotprototype.data.response.Team
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamPostDetailViewModel @Inject constructor(
    private val groupService: GroupService,
    private val teamBuildingService: TeamBuildingService
) : ViewModel() {

    private val _teamDetailData = MutableLiveData<Team>()
    val teamDetailData: LiveData<Team> = _teamDetailData

    private val _isDuplicateGroupPerson = MutableLiveData<Boolean>()
    val isDuplicateGroupPerson: LiveData<Boolean> = _isDuplicateGroupPerson

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
}