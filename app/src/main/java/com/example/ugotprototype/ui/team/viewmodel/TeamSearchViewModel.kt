package com.example.ugotprototype.ui.team.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.ApiService
import com.example.ugotprototype.data.api.TeamBuildingService
import com.example.ugotprototype.data.response.Team
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

    fun searchTeams(query: String) {
        _isLoadingPage.value = false
        viewModelScope.launch {
            kotlin.runCatching {
                val allMatchingTeams = mutableListOf<Team>()
                var currentPage = 0

                while (true) {
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
                    if (currentPage >= (response.body()?.totalPages!! - 1)) {
                        break
                    }
                    currentPage++
                }
                _teams.value = allMatchingTeams
            }.onSuccess {
                _isLoadingPage.value = true
            }.onFailure {
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
}