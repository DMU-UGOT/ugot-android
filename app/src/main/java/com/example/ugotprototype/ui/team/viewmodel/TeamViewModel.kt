package com.example.ugotprototype.ui.team.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ugotprototype.data.TeamData

class TeamViewModel : ViewModel() {
    private val _teamItemList = MutableLiveData<ArrayList<TeamData>>()
    val teamItemList: LiveData<ArrayList<TeamData>> = _teamItemList

    fun setTeamData(teamData: ArrayList<TeamData>) {
        _teamItemList.value = teamData
    }
}