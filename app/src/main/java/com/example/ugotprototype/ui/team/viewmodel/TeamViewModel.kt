package com.example.ugotprototype.ui.team.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ugotprototype.data.team.TeamData

class TeamViewModel : ViewModel() {
    private val _teamItemList = MutableLiveData<ArrayList<TeamData>>()
    val teamItemList: LiveData<ArrayList<TeamData>> = _teamItemList

    private val _isTeamPostRegisterBtnEnabled = MutableLiveData<Boolean>()
    var isTeamPostRegisterBtnEnabled: LiveData<Boolean> = _isTeamPostRegisterBtnEnabled

    private val _teamMaxPersonnel = MutableLiveData<Int>()
    var teamMaxPersonnel: LiveData<Int> = _teamMaxPersonnel

    fun setTeamData(teamData: ArrayList<TeamData>) {
        _teamItemList.value = teamData
    }

    fun isTeamPostRegisterButtonState(enabled: Boolean) {
        _isTeamPostRegisterBtnEnabled.value = enabled
    }

    fun teamMaxPersonnel(maxPersonnel: Int) {
        _teamMaxPersonnel.value = maxPersonnel
    }
}