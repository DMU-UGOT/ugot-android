package com.example.ugotprototype.ui.team.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.response.OrgMemberDataResponse
import com.example.ugotprototype.di.api.ApiService
import com.example.ugotprototype.ui.team.view.TeamFragment
import com.example.ugotprototype.ui.team.view.TeamInformationActivity.Companion.DUMMY_DATA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamInformationViewModel @Inject constructor(private val apiService: ApiService): ViewModel() {
    private val _teamInforList = MutableLiveData<List<OrgMemberDataResponse>>()
    var isTeamInforList: LiveData<List<OrgMemberDataResponse>> = _teamInforList

    fun getTeamInformationList() {
        viewModelScope.launch {
            kotlin.runCatching {
                _teamInforList.value = apiService.getOrganizationMembers(DUMMY_DATA, "Bearer ${TeamFragment.TOKEN_DATA}")
            }
        }
    }
}