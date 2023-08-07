package com.example.ugotprototype.ui.team.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ugotprototype.data.team.TeamData
import com.example.ugotprototype.di.api.response.OrgMemberDataResponse
import com.example.ugotprototype.di.api.response.TeamPostResponse

class TeamViewModel : ViewModel() {
    private val _teamItemList = MutableLiveData<List<TeamPostResponse>>()
    val teamItemList: LiveData<List<TeamPostResponse>> = _teamItemList

    private val _isTeamPostRegisterBtnEnabled = MutableLiveData<Boolean>()
    var isTeamPostRegisterBtnEnabled: LiveData<Boolean> = _isTeamPostRegisterBtnEnabled

    private val _teamMaxPersonnel = MutableLiveData<Int>()
    var teamMaxPersonnel: LiveData<Int> = _teamMaxPersonnel

    private val _teamInforList = MutableLiveData<List<OrgMemberDataResponse>>()
    var isTeamInforList: LiveData<List<OrgMemberDataResponse>> = _teamInforList

    private val _postLastPage =  MutableLiveData<Int>()
    var postLastPage: LiveData<Int> =  _postLastPage

    fun setTeamData(teamData: List<TeamPostResponse>) {
        _teamItemList.value = teamData
    }

    fun isTeamPostRegisterButtonState(enabled: Boolean) {
        _isTeamPostRegisterBtnEnabled.value = enabled
    }

    fun teamMaxPersonnel(maxPersonnel: Int) {
        _teamMaxPersonnel.value = maxPersonnel
    }

    fun setTeamInforData(teamInforData: List<OrgMemberDataResponse>) {
        _teamInforList.value = teamInforData
    }

    fun setPostLastPage(pageNum: Int){
        _postLastPage.value = pageNum
    }
}