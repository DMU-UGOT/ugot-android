package com.example.ugotprototype.ui.team.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.ApiService
import com.example.ugotprototype.data.api.TeamBuildingService
import com.example.ugotprototype.data.response.TeamPostResponse
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

    private val _isTokenExpired = MutableLiveData<Boolean>()
    val isTokenExpired: LiveData<Boolean> = _isTokenExpired

    private val _isLoadingPage = MutableLiveData<Boolean>()
    val isLoadingPage: LiveData<Boolean> = _isLoadingPage

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
        _isLoadingPage.value = false
        viewModelScope.launch {
            kotlin.runCatching {
                val pageResponse = teamBuildingService.getTeams(_currentPage.value!!, 5)
                val teamsResponse = pageResponse.data

                teamsResponse.forEach { team ->
                    kotlin.runCatching {
                        val avatarUrl = apiService.getOrganization(
                            team.gitHubLink
                        )?.avatarUrl
                        team.avatarUrl = avatarUrl ?: ""
                    }
                }

                _teamItemList.value = teamsResponse
                _totalPage.value = pageResponse.pageInfo.totalPages
            }.onSuccess {
                _isLoadingPage.value = true
            }.onFailure {
                _isTokenExpired.value = true
            }
        }
    }

}