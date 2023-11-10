package com.example.ugotprototype.ui.team.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.ApiService
import com.example.ugotprototype.data.api.TeamBuildingService
import com.example.ugotprototype.data.response.Team
import com.example.ugotprototype.data.team.TeamSearchHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamSearchViewModel @Inject constructor(
    private val apiService: ApiService, private val teamBuildingService: TeamBuildingService
) : ViewModel() {

    private val _teams = MutableLiveData<List<Team>>()
    val teams: LiveData<List<Team>> = _teams

    private val _isLoadingPage = MutableLiveData<Boolean>()
    val isLoadingPage: LiveData<Boolean> = _isLoadingPage

    private val _postSearchHistory = MutableLiveData<List<TeamSearchHistory>>()
    val postSearchHistory: LiveData<List<TeamSearchHistory>> = _postSearchHistory

    private val _isDelete = MutableLiveData<Boolean>()
    val isDelete: LiveData<Boolean> = _isDelete

    private val _isAllDelete = MutableLiveData<Boolean>()
    val isAllDelete: LiveData<Boolean> = _isAllDelete

    private val _isSearch = MutableLiveData<Boolean>()
    val isSearch: LiveData<Boolean> = _isSearch

    fun searchTeams(query: String) {
        _isLoadingPage.value = false
        viewModelScope.launch {
            kotlin.runCatching {
                val allMatchingTeams = mutableListOf<Team>()
                var currentPage = 0

                val response = teamBuildingService.searchTeams(currentPage, query)
                val teams = response.body()?.content ?: emptyList()

                val bookmark = teamBuildingService.getBookmark()
                val teamId = bookmark.map { it.teamId }

                for (team in teams) {
                    team.bookmark = team.teamId in teamId
                    if (team.title.contains(query)) {
                        val avatarUrl = apiService.getOrganization(
                            team.gitHubLink
                        )?.avatarUrl
                        team.avatarUrl = avatarUrl ?: ""
                        allMatchingTeams.add(team)
                    }
                }

                _teams.value = allMatchingTeams
            }.onSuccess {
                _isSearch.value = true
                _isLoadingPage.value = true
            }.onFailure {
                _isSearch.value = false
                _isLoadingPage.value = true
            }
        }
    }

    fun sendBookmark(teamId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                teamBuildingService.setBookmark(teamId)
            }
        }
    }

    fun getTeamPostSearchHistory() {
        viewModelScope.launch {
            kotlin.runCatching {
                teamBuildingService.searchHistory()
            }.onSuccess {
                _postSearchHistory.value = it
            }
        }
    }

    fun deleteTeamPostSearchHistory(query: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                teamBuildingService.deleteSearchHistory(query)
            }.onSuccess {
                _isDelete.value = true
            }.onFailure {
                _isDelete.value = false
            }
        }
    }

    fun allDeleteTeamPostSearchHistory() {
        viewModelScope.launch {
            kotlin.runCatching {
                teamBuildingService.allDeleteSearchHistory()
            }.onSuccess {
                _isAllDelete.value = true
            }.onFailure {
                _isAllDelete.value = false
            }
        }
    }
}