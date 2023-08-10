package com.example.ugotprototype.ui.team.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.response.TeamPostResponse
import com.example.ugotprototype.di.api.ApiService
import com.example.ugotprototype.di.api.TeamBuildingService
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TOKEN_DATA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamViewModel @Inject constructor(
    private val apiService: ApiService, private val teamBuildingService: TeamBuildingService
) : ViewModel() {
    private val _teamItemList = MutableLiveData<List<TeamPostResponse>>()
    val teamItemList: LiveData<List<TeamPostResponse>> = _teamItemList

    private val _totalPage = MutableLiveData<Int>()
    var totalPage: LiveData<Int> = _totalPage

    private val _currentPage = MutableLiveData<Int>()
    val currentPage: LiveData<Int> = _currentPage

    val onPrevButtonClickListener = View.OnClickListener {
        if (_currentPage.value!! > 1) {
            _currentPage.value = _currentPage.value!! - 1
        }
    }

    val onNextButtonClickListener = View.OnClickListener {
        if (_currentPage.value!! < _totalPage.value!!) {
            _currentPage.value = _currentPage.value!! + 1
        }
    }

    fun setCurrentPage(data: Int) {
        _currentPage.value = data
    }

    fun setTotalPage(data: Int) {
        _totalPage.value = data
    }

    fun getTeamList() {
        viewModelScope.launch {
            kotlin.runCatching {
                val pageResponse = teamBuildingService.getTeams(_currentPage.value!!, 5)
                val teamsResponse = pageResponse.data

                teamsResponse.forEach { team ->
                    kotlin.runCatching {
                        val avatarUrl = apiService.getOrganization(
                            team.gitHubLink, "Bearer $TOKEN_DATA"
                        )?.avatarUrl
                        team.avatarUrl = avatarUrl ?: ""
                    }
                }

                _teamItemList.value = teamsResponse
                _totalPage.value = pageResponse.pageInfo.totalPages
            }
        }
    }
}